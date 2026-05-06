package com.fintech.notification_services.kafka;

import com.fintech.notification_services.dto.PaymentEvent;
import com.fintech.notification_services.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void consume(PaymentEvent event) {
        log.info("📨 Received event: {}", event);
        if ("SUCCESS".equals(event.getStatus())) {
            notificationService.notifyPaymentSuccess(event);
        } else if ("FAILED".equals(event.getStatus())) {
            notificationService.notifyPaymentFailed(event);
        }
    }
}