package com.fintech.payment_services.feign;

import com.fintech.payment_services.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "fraud-service", url = "http://fraud-service:8086")
public interface FraudClient {

    @PostMapping("/api/fraud/check")
    Map<String, String> check(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> request);
}