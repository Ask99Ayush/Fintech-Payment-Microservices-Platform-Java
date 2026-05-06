package com.fintech.payment_services.dto;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class PaymentResponse {
    private Long id;
    private String idempotencyKey;
    private String senderEmail;
    private String receiverEmail;
    private BigDecimal amount;
    private String status;
    private String failureReason;
    private LocalDateTime createdAt;
}