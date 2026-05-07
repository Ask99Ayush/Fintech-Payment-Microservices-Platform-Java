package com.fintech.payment_services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "wallet-service", url = "http://wallet-service:8083")
public interface WalletClient {

    @PostMapping("/api/wallet/debit/internal")
    void debit(
            @RequestHeader("Authorization") String token,
            @RequestParam String email,
            @RequestParam BigDecimal amount,
            @RequestParam String idempotencyKey);

    @PostMapping("/api/wallet/credit/{email}")
    void credit(
            @RequestHeader("Authorization") String token,
            @PathVariable String email,
            @RequestParam BigDecimal amount);
}