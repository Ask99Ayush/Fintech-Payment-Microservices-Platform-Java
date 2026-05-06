package com.fintech.wallet_services.controller;


import com.fintech.wallet_services.dto.*;
import com.fintech.wallet_services.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<WalletResponse> create(Authentication auth) {
        return ResponseEntity.ok(walletService.createWallet(auth.getName()));
    }

    @GetMapping
    public ResponseEntity<WalletResponse> get(Authentication auth) {
        return ResponseEntity.ok(walletService.getWallet(auth.getName()));
    }

    @PostMapping("/topup")
    public ResponseEntity<WalletResponse> topUp(Authentication auth,
                                                @Valid @RequestBody TopUpRequest request) {
        return ResponseEntity.ok(walletService.topUp(auth.getName(), request));
    }

    @PostMapping("/debit")
    public ResponseEntity<WalletResponse> debit(Authentication auth,
                                                @Valid @RequestBody DebitRequest request) {
        return ResponseEntity.ok(walletService.debit(auth.getName(), request));
    }

    // Internal endpoint called by Payment Service
    @PostMapping("/credit/{email}")
    public ResponseEntity<WalletResponse> credit(@PathVariable String email,
                                                 @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(walletService.credit(email, amount));
    }
}