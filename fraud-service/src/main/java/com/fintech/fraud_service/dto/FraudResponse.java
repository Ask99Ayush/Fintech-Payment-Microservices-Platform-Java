package com.fintech.fraud_service.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FraudResponse {
    private String result;  // APPROVED or REJECTED
    private String reason;
}