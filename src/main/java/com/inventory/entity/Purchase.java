package com.inventory.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.inventory.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase", indexes = {
    @Index(name = "idx_purchase_invoice_number", columnList = "invoice_number"),
    @Index(name = "idx_purchase_purchase_date", columnList = "purchase_date"),
    @Index(name = "idx_purchase_customer_id", columnList = "customer_id"),
    @Index(name = "idx_purchase_client_id", columnList = "client_id")
})
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "total_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "purchase_date")
    private OffsetDateTime purchaseDate;
    
    @Column(length = 29, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt = OffsetDateTime.now();
    
    @Column(length = 29, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt = OffsetDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_created_by_user_master_id"))
    private UserMaster createdBy;
    
    @Column(name = "invoice_number")
    private String invoiceNumber;
    
    @Column(name = "other_expenses", precision = 17, scale = 2)
    private BigDecimal otherExpenses;

    @Column(name = "discount", precision = 6, scale = 4)
    private BigDecimal discount;
    
    @Column(name = "discount_amount", precision = 17, scale = 2)
    private BigDecimal discountAmount;
    
    @Column(name = "discount_price", precision = 17, scale = 2)
    private BigDecimal discountPrice;
    
    @Column(name = "total_other_expenses", precision = 17, scale = 2)
    private BigDecimal totalOtherExpenses;
    
    @Column(name = "net_amount", precision = 17, scale = 2)
    private BigDecimal netAmount;
    
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> items = new ArrayList<>();
    
    @Column(name = "payment_reference")
    private String paymentReference;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", 
        foreignKey = @ForeignKey(name = "fk_purchase_customer_id_customer_id"))
    private Customer customer;

    @Column(name = "remarks", columnDefinition = "varchar ")
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_client_id_client_id"))
    private Client client;
}