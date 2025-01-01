package com.inventory.entity;

import com.inventory.enums.PurchaseOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.function.Supplier;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_order", indexes = {
    @Index(name = "idx_purchase_order_date", columnList = "order_date"),
    @Index(name = "idx_purchase_order_supplier_id", columnList = "supplier_id"),
    @Index(name = "idx_purchase_order_client_id", columnList = "client_id")
})
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    
    @Column(name = "order_date", nullable = false)
    private OffsetDateTime orderDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @Column(name = "expected_delivery_date")
    private OffsetDateTime expectedDeliveryDate;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PurchaseOrderStatus status = PurchaseOrderStatus.PENDING;
    
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();
    
    @Column(name = "total_amount", precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "remarks")
    private String remarks;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserMaster createdBy;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
} 