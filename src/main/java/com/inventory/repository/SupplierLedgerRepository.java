package com.inventory.repository;

import com.inventory.entity.SupplierLedger;
import com.inventory.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierLedgerRepository extends JpaRepository<SupplierLedger, Long> {
    @Query("""
        SELECT COALESCE(SUM(CASE 
            WHEN sl.transactionType = 'DEBIT' THEN sl.debitAmount 
            ELSE sl.creditAmount * -1 
        END), 0)
        FROM SupplierLedger sl 
        WHERE sl.supplier.id = :supplierId
    """)
    Optional<BigDecimal> findCurrentBalance(Long supplierId);
    
    @Query("""
        SELECT sl FROM SupplierLedger sl 
        WHERE sl.supplier.id = :supplierId 
        AND sl.transactionDate BETWEEN :startDate AND :endDate 
        ORDER BY sl.transactionDate
    """)
    List<SupplierLedger> findLedgerEntries(Long supplierId, OffsetDateTime startDate, OffsetDateTime endDate);
} 