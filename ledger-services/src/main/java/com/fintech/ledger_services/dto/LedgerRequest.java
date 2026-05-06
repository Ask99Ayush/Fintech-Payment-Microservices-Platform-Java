package com.fintech.ledger_services.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LedgerRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String type;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String referenceId;
    private String description;
}