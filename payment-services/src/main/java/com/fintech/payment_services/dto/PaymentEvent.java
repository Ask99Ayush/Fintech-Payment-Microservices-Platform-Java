package com.fintech.payment_services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {

    private String senderEmail;

    private String receiverEmail;

    private BigDecimal amount;

    private String status;

    private String idempotencyKey;

    private String transactionType;

    private String description;

    private boolean refund;

    private String createdAt;
}
