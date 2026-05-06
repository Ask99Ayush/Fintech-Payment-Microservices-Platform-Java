package com.fintech.payment_services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wallet-service", url = "http://localhost:8083")
public interface WalletClient {

    @PostMapping("/api/wallet/debit")
    void debit(@RequestParam String email, @RequestParam java.math.BigDecimal amount,
               @RequestParam String idempotencyKey);

    @PostMapping("/api/wallet/credit/{email}")
    void credit(@PathVariable String email, @RequestParam java.math.BigDecimal amount);
}