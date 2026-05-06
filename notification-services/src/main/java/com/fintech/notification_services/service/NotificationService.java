package com.fintech.notification_services.service;

import com.fintech.notification_services.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void notifyPaymentSuccess(PaymentEvent event) {
        // In production → send real email via JavaMailSender
        log.info("✅ SUCCESS EMAIL to sender: {} | Amount: {} sent to {}",
                event.getSenderEmail(), event.getAmount(), event.getReceiverEmail());
        log.info("✅ SUCCESS EMAIL to receiver: {} | Amount: {} received from {}",
                event.getReceiverEmail(), event.getAmount(), event.getSenderEmail());
    }

    public void notifyPaymentFailed(PaymentEvent event) {
        log.info("❌ FAILED EMAIL to sender: {} | Amount: {} | Reason: payment failed",
                event.getSenderEmail(), event.getAmount());
    }
}