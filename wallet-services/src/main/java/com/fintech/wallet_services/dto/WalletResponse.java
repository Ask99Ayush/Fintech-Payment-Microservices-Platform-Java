package com.fintech.wallet_services.dto;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
public class WalletResponse {
    private Long id;
    private String email;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;
}