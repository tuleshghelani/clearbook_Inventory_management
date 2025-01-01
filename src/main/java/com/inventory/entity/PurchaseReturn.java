package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_return", indexes = {
    @Index(name = "idx_purchase_return_date", columnList = "return_date"),
    @Index(name = "idx_purchase_return_purchase_id", columnList = "purchase_id"),
    @Index(name = "idx_purchase_return_client_id", columnList = "client_id")
})
public class PurchaseReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;
    
    @Column(name = "return_date", nullable = false)
    private OffsetDateTime returnDate;
    
    @Column(name = "return_number", nullable = false)
    private String returnNumber;
    
    @Column(name = "total_amount", precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "total_tax_amount", precision = 17, scale = 2)
    private BigDecimal totalTaxAmount;
    
    @Column(name = "remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "purchaseReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseReturnItem> items = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserMaster createdBy;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
} 