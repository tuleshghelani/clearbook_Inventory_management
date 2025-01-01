package com.inventory.entity;

import com.inventory.enums.MovementType;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "stock_movement", indexes = {
    @Index(name = "idx_stock_movement_product_id", columnList = "product_id"),
    @Index(name = "idx_stock_movement_movement_date", columnList = "movement_date"),
    @Index(name = "idx_stock_movement_reference_type", columnList = "reference_type"),
    @Index(name = "idx_stock_movement_client_id", columnList = "client_id")
})
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "movement_date", nullable = false)
    private OffsetDateTime movementDate;

    @Column(name = "movement_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 17, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "reference_type", nullable = false)
    private String referenceType; // PURCHASE, SALE, RETURN, ADJUSTMENT

    @Column(name = "reference_id")
    private Long referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
} 