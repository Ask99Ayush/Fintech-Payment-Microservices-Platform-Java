package com.fintech.fraud_service.service;


import com.fintech.fraud_service.dto.*;
import com.fintech.fraud_service.entity.FraudCheck;
import com.fintech.fraud_service.repository.FraudCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudService {

    private final FraudCheckRepository fraudCheckRepository;

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000");
    private static final int MAX_TRANSACTIONS_PER_MINUTE = 5;

    public FraudResponse check(FraudRequest request) {
        // Rule 1 — duplicate payment
        if (fraudCheckRepository.existsByReferenceId(request.getReferenceId())) {
            return save(request, "REJECTED", "Duplicate transaction");
        }

        // Rule 2 — amount too high
        if (request.getAmount().compareTo(MAX_AMOUNT) > 0) {
            return save(request, "REJECTED", "Amount exceeds limit of $10,000");
        }

        // Rule 3 — too many transactions in last 1 minute
        long recentCount = fraudCheckRepository.countByEmailAndCreatedAtAfter(
                request.getEmail(), LocalDateTime.now().minusMinutes(1));
        if (recentCount >= MAX_TRANSACTIONS_PER_MINUTE) {
            return save(request, "REJECTED", "Too many transactions in short time");
        }

        return save(request, "APPROVED", "All checks passed");
    }

    private FraudResponse save(FraudRequest request, String result, String reason) {
        FraudCheck check = FraudCheck.builder()
                .email(request.getEmail())
                .amount(request.getAmount())
                .referenceId(request.getReferenceId())
                .result(result)
                .reason(reason)
                .build();
        fraudCheckRepository.save(check);
        return FraudResponse.builder().result(result).reason(reason).build();
    }
}