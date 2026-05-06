package com.fintech.payment_services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ledger-service", url = "http://localhost:8085")
public interface LedgerClient {

    @PostMapping("/api/ledger/record")
    void record(@RequestBody Map<String, Object> request);
}