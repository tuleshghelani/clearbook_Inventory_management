package com.inventory.entity;

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
@Table(name = "purchase_return_item", indexes = {
    @Index(name = "idx_purchase_return_item_product_id", columnList = "product_id"),
    @Index(name = "idx_purchase_return_item_purchase_return_id", columnList = "purchase_return_id")
})
public class PurchaseReturnItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_return_id", nullable = false)
    private PurchaseReturn purchaseReturn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_item_id", nullable = false)
    private PurchaseItem purchaseItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", precision = 17, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "total_amount", precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    // GST fields from PurchaseItem
    @Column(name = "cgst_percentage", precision = 5, scale = 2)
    private BigDecimal cgstPercentage;
    
    @Column(name = "sgst_percentage", precision = 5, scale = 2)
    private BigDecimal sgstPercentage;
    
    @Column(name = "igst_percentage", precision = 5, scale = 2)
    private BigDecimal igstPercentage;
    
    @Column(name = "cgst_amount", precision = 17, scale = 2)
    private BigDecimal cgstAmount;
    
    @Column(name = "sgst_amount", precision = 17, scale = 2)
    private BigDecimal sgstAmount;
    
    @Column(name = "igst_amount", precision = 17, scale = 2)
    private BigDecimal igstAmount;
} 