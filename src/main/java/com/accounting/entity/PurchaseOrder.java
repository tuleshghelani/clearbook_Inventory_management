package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import com.accounting.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "purchase_orders", indexes = {
    @Index(name = "idx_po_party_id", columnList = "party_id"),
    @Index(name = "idx_po_order_date", columnList = "order_date"),
    @Index(name = "idx_po_client_id", columnList = "client_id")
})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    @Column(name = "order_number", nullable = false, length = 32)
    private String orderNumber;
    
    @Column(name = "order_date", nullable = false)
    private OffsetDateTime orderDate;
    
    @Column(name = "delivery_date")
    private OffsetDateTime deliveryDate;
    
    @Column(name = "total_amount", precision = 17, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @Column(name = "total_tax", precision = 17, scale = 2)
    private BigDecimal totalTax = BigDecimal.ZERO;
    
    @Column(name = "total_discount", precision = 17, scale = 2)
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    
    @Column(name = "net_amount", precision = 17, scale = 2)
    private BigDecimal netAmount = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private PurchaseOrderStatus status = PurchaseOrderStatus.PENDING;
    
    @Column(name = "remarks", length = 512)
    private String remarks;
} 