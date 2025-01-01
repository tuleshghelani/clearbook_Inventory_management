package com.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SupplierLedgerService {
    private final SupplierLedgerRepository supplierLedgerRepository;
    private final UtilityService utilityService;

    @Transactional
    public void recordPurchaseTransaction(Purchase purchase) {
        SupplierLedger ledgerEntry = new SupplierLedger();
        ledgerEntry.setSupplier(purchase.getSupplier());
        ledgerEntry.setTransactionDate(purchase.getPurchaseDate());
        ledgerEntry.setTransactionType(TransactionType.DEBIT);
        ledgerEntry.setReferenceType("PURCHASE");
        ledgerEntry.setReferenceId(purchase.getId());
        ledgerEntry.setReferenceNumber(purchase.getInvoiceNumber());
        ledgerEntry.setDebitAmount(purchase.getTotalAmount());
        ledgerEntry.setRemarks("Purchase Invoice: " + purchase.getInvoiceNumber());
        ledgerEntry.setClient(purchase.getClient());
        
        // Calculate running balance
        BigDecimal currentBalance = getCurrentBalance(purchase.getSupplier().getId());
        ledgerEntry.setBalanceAmount(currentBalance.add(purchase.getTotalAmount()));
        
        supplierLedgerRepository.save(ledgerEntry);
    }

    private BigDecimal getCurrentBalance(Long supplierId) {
        return supplierLedgerRepository.findCurrentBalance(supplierId)
            .orElse(BigDecimal.ZERO);
    }
} 