package com.fintech.fraud_service.controller;

import com.fintech.fraud_service.dto.*;
import com.fintech.fraud_service.service.FraudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
public class FraudController {

    private final FraudService fraudService;

    // Internal endpoint — called by Payment Service only
    @PostMapping("/check")
    public ResponseEntity<FraudResponse> check(@Valid @RequestBody FraudRequest request) {
        return ResponseEntity.ok(fraudService.check(request));
    }
}