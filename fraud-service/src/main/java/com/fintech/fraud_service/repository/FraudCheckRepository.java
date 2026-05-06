package com.fintech.fraud_service.repository;


import com.fintech.fraud_service.entity.FraudCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FraudCheckRepository extends JpaRepository<FraudCheck, Long> {
    long countByEmailAndCreatedAtAfter(String email, LocalDateTime after);
    boolean existsByReferenceId(String referenceId);
}