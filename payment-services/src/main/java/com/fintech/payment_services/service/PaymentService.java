package com.fintech.payment_services.service;

import com.fintech.payment_services.dto.*;
import com.fintech.payment_services.entity.Payment;
import com.fintech.payment_services.feign.*;
import com.fintech.payment_services.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final WalletClient walletClient;
    private final FraudClient fraudClient;
    private final LedgerClient ledgerClient;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Transactional
    public PaymentResponse processPayment(String senderEmail, PaymentRequest request,String token) {

        // 1 — Idempotency check
        var existing = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
        if (existing.isPresent()) return mapToResponse(existing.get());

        // 2 — Save as PENDING
        Payment payment = Payment.builder()
                .idempotencyKey(request.getIdempotencyKey())
                .senderEmail(senderEmail)
                .receiverEmail(request.getReceiverEmail())
                .amount(request.getAmount())
                .build();
        paymentRepository.save(payment);

        try {
            // 3 — Fraud check
            Map<String, Object> fraudReq = Map.of(
                    "email", senderEmail,
                    "amount", request.getAmount(),
                    "referenceId", request.getIdempotencyKey()
            );
            Map<String, String> fraudResult = fraudClient.check(token,fraudReq);
            if ("REJECTED".equals(fraudResult.get("result"))) {
                payment.setStatus("FAILED");
                payment.setFailureReason(fraudResult.get("reason"));
                paymentRepository.save(payment);
                publishEvent(payment);
                return mapToResponse(payment);
            }

            // 4 — Debit sender
            walletClient.debit(token,senderEmail, request.getAmount(), request.getIdempotencyKey());

            // 5 — Record debit in ledger
            ledgerClient.record(token,Map.of(
                    "email", senderEmail,
                    "type", "DEBIT",
                    "amount", request.getAmount(),
                    "referenceId", request.getIdempotencyKey(),
                    "description", "Payment to " + request.getReceiverEmail()
            ));

            // 6 — Credit receiver
            walletClient.credit(token,request.getReceiverEmail(), request.getAmount());

            // 7 — Record credit in ledger
            ledgerClient.record(token,Map.of(
                    "email", request.getReceiverEmail(),
                    "type", "CREDIT",
                    "amount", request.getAmount(),
                    "referenceId", request.getIdempotencyKey() + "-credit",
                    "description", "Payment from " + senderEmail
            ));

            // 8 — Mark SUCCESS
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            publishEvent(payment);
            return mapToResponse(payment);

        } catch (Exception e) {
            log.error("Payment failed: {}", e.getMessage());

            // Compensating transaction — refund if debit happened
            try {
                walletClient.credit(token,senderEmail, request.getAmount());
                log.info("Compensating credit issued to {}", senderEmail);
            } catch (Exception ex) {
                log.error("Compensation failed: {}", ex.getMessage());
            }

            payment.setStatus("FAILED");
            payment.setFailureReason(e.getMessage());
            paymentRepository.save(payment);
            publishEvent(payment);
            return mapToResponse(payment);
        }
    }

    public List<PaymentResponse> getHistory(String email) {
        return paymentRepository.findBySenderEmailOrderByCreatedAtDesc(email)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private void publishEvent(Payment p) {
        kafkaTemplate.send("payment-events", new PaymentEvent(
                p.getSenderEmail(), p.getReceiverEmail(),
                p.getAmount(), p.getStatus(), p.getIdempotencyKey()));
    }

    private PaymentResponse mapToResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .idempotencyKey(p.getIdempotencyKey())
                .senderEmail(p.getSenderEmail())
                .receiverEmail(p.getReceiverEmail())
                .amount(p.getAmount())
                .status(p.getStatus())
                .failureReason(p.getFailureReason())
                .createdAt(p.getCreatedAt())
                .build();
    }
}