package com.fintech.ledger_services.dto;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class LedgerResponse {
    private Long id;
    private String email;
    private String type;
    private BigDecimal amount;
    private String referenceId;
    private String description;
    private LocalDateTime createdAt;
}