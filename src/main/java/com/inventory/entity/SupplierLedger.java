package com.inventory.entity;

import com.inventory.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "supplier_ledger", indexes = {
    @Index(name = "idx_supplier_ledger_supplier_id", columnList = "supplier_id"),
    @Index(name = "idx_supplier_ledger_transaction_date", columnList = "transaction_date"),
    @Index(name = "idx_supplier_ledger_client_id", columnList = "client_id")
})
public class SupplierLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @Column(name = "transaction_date", nullable = false)
    private OffsetDateTime transactionDate;
    
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    
    @Column(name = "reference_type", nullable = false)
    private String referenceType; // PURCHASE, PAYMENT, RETURN
    
    @Column(name = "reference_id")
    private Long referenceId;
    
    @Column(name = "reference_number")
    private String referenceNumber;
    
    @Column(name = "debit_amount", precision = 17, scale = 2)
    private BigDecimal debitAmount = BigDecimal.ZERO;
    
    @Column(name = "credit_amount", precision = 17, scale = 2)
    private BigDecimal creditAmount = BigDecimal.ZERO;
    
    @Column(name = "balance_amount", precision = 17, scale = 2)
    private BigDecimal balanceAmount;
    
    @Column(name = "remarks")
    private String remarks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
} 