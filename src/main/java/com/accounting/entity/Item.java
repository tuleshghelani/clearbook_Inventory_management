package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "items", indexes = {
    @Index(name = "idx_item_code", columnList = "code"),
    @Index(name = "idx_item_category_id", columnList = "category_id"),
    @Index(name = "idx_item_client_id", columnList = "client_id")
})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {
    
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    
    @Column(name = "code", nullable = false, length = 32)
    private String code;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ItemCategory category;
    
    @Column(name = "description", length = 512)
    private String description;
    
    @Column(name = "unit", length = 32)
    private String unit;
    
    @Column(name = "hsn_code", length = 32)
    private String hsnCode;
    
    @Column(name = "purchase_price", precision = 17, scale = 2)
    private BigDecimal purchasePrice;
    
    @Column(name = "sale_price", precision = 17, scale = 2)
    private BigDecimal salePrice;
    
    @Column(name = "minimum_stock", precision = 17, scale = 2)
    private BigDecimal minimumStock;
    
    @Column(name = "current_stock", precision = 17, scale = 2)
    private BigDecimal currentStock;
    
    @Column(name = "status", nullable = false, length = 2)
    private String status = "A";
} 