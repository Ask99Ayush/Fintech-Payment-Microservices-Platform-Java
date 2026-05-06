package com.fintech.ledger_services.service;

import com.fintech.ledger_services.dto.*;
import com.fintech.ledger_services.entity.LedgerEntry;
import com.fintech.ledger_services.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    public LedgerResponse record(LedgerRequest request) {
        if (ledgerRepository.existsByReferenceId(request.getReferenceId()))
            throw new RuntimeException("Duplicate entry: " + request.getReferenceId());

        LedgerEntry entry = LedgerEntry.builder()
                .email(request.getEmail())
                .type(request.getType())
                .amount(request.getAmount())
                .referenceId(request.getReferenceId())
                .description(request.getDescription())
                .build();

        ledgerRepository.save(entry);
        return mapToResponse(entry);
    }

    public List<LedgerResponse> getHistory(String email) {
        return ledgerRepository.findByEmailOrderByCreatedAtDesc(email)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private LedgerResponse mapToResponse(LedgerEntry e) {
        return LedgerResponse.builder()
                .id(e.getId())
                .email(e.getEmail())
                .type(e.getType())
                .amount(e.getAmount())
                .referenceId(e.getReferenceId())
                .description(e.getDescription())
                .createdAt(e.getCreatedAt())
                .build();
    }
}