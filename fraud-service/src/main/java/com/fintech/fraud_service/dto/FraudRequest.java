package com.fintech.fraud_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class FraudRequest {
    @NotBlank
    private String email;
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String referenceId;
}