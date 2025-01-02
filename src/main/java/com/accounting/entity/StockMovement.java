package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stock_movements", indexes = {
    @Index(name = "idx_stock_item_id", columnList = "item_id"),
    @Index(name = "idx_stock_reference_type", columnList = "reference_type"),
    @Index(name = "idx_stock_reference_id", columnList = "reference_id"),
    @Index(name = "idx_stock_client_id", columnList = "client_id")
})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Column(name = "reference_type", nullable = false, length = 32)
    private String referenceType; // PURCHASE, SALE, ADJUSTMENT
    
    @Column(name = "reference_id")
    private Long referenceId;
    
    @Column(name = "quantity", precision = 17, scale = 2, nullable = false)
    private BigDecimal quantity;
    
    @Column(name = "unit_price", precision = 17, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "remarks", length = 512)
    private String remarks;
    
    @Column(name = "status", nullable = false, length = 2)
    private String status = "A";
} 