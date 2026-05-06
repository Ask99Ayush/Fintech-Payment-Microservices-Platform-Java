package com.fintech.ledger_services.controller;


import com.fintech.ledger_services.dto.*;
import com.fintech.ledger_services.service.LedgerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;

    // Internal endpoint — called by Payment Service
    @PostMapping("/record")
    public ResponseEntity<LedgerResponse> record(@Valid @RequestBody LedgerRequest request) {
        return ResponseEntity.ok(ledgerService.record(request));
    }

    // User endpoint — get own transaction history
    @GetMapping("/history")
    public ResponseEntity<List<LedgerResponse>> history(Authentication auth) {
        return ResponseEntity.ok(ledgerService.getHistory(auth.getName()));
    }
}