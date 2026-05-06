package com.fintech.payment_services.feign;

import com.fintech.payment_services.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "fraud-service", url = "http://localhost:8086")
public interface FraudClient {

    @PostMapping("/api/fraud/check")
    Map<String, String> check(@RequestBody Map<String, Object> request);
}