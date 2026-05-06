package com.fintech.wallet_services.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TopUpRequest {
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    private String currency = "USD";
}