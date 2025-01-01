package com.inventory.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "gst_slab", indexes = {
    @Index(name = "idx_gst_slab_name", columnList = "name"),
    @Index(name = "idx_gst_slab_client_id", columnList = "client_id")
})
public class GstSlab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "cgst_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal cgstPercentage;
    
    @Column(name = "sgst_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal sgstPercentage;
    
    @Column(name = "igst_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal igstPercentage;
    
    @Column(name = "hsn_code")
    private String hsnCode;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
} 