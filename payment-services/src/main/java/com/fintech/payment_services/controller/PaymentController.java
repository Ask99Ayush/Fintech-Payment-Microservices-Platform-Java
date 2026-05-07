package com.fintech.payment_services.controller;


import com.fintech.payment_services.dto.*;
import com.fintech.payment_services.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/send")
    public ResponseEntity<PaymentResponse> send(
            Authentication auth,
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(auth.getName(), request, token));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PaymentResponse>> history(Authentication auth) {
        return ResponseEntity.ok(paymentService.getHistory(auth.getName()));
    }
}