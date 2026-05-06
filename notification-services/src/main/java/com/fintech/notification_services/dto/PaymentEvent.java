package com.fintech.notification_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class PaymentEvent {
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private String status;
    private String idempotencyKey;
}