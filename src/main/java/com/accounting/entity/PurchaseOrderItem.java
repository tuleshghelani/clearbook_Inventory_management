package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items", indexes = {
    @Index(name = "idx_poi_purchase_order_id", columnList = "purchase_order_id"),
    @Index(name = "idx_poi_item_id", columnList = "item_id"),
    @Index(name = "idx_poi_client_id", columnList = "client_id")
})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Column(name = "quantity", precision = 17, scale = 2, nullable = false)
    private BigDecimal quantity;
    
    @Column(name = "unit_price", precision = 17, scale = 2, nullable = false)
    private BigDecimal unitPrice;
    
    @Column(name = "tax_percentage", precision = 5, scale = 2)
    private BigDecimal taxPercentage;
    
    @Column(name = "tax_amount", precision = 17, scale = 2)
    private BigDecimal taxAmount;
    
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(name = "discount_amount", precision = 17, scale = 2)
    private BigDecimal discountAmount;
    
    @Column(name = "total_amount", precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "net_amount", precision = 17, scale = 2)
    private BigDecimal netAmount;
    
    @Column(name = "received_quantity", precision = 17, scale = 2)
    private BigDecimal receivedQuantity = BigDecimal.ZERO;
} 