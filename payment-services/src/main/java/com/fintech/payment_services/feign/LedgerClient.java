package com.fintech.payment_services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "ledger-service", url = "http://ledger-service:8085")
public interface LedgerClient {

    @PostMapping("/api/ledger/record")
    void record(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> request);
}