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
@Table(name = "purchase_item", indexes = {
    @Index(name = "idx_purchase_item_product_id", columnList = "product_id"),
    @Index(name = "idx_purchase_item_category_id", columnList = "category_id"),
    @Index(name = "idx_purchase_item_remaining_quantity", columnList = "remaining_quantity"),
    @Index(name = "idx_purchase_item_purchase_id", columnList = "purchase_id"),
    @Index(name = "idx_purchase_item_client_id", columnList = "client_id")
})
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_item_purchase_id_purchase_id"))
    private Purchase purchase;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_item_product_id_product_id"))
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_item_category_id_category_id"))
    private Category category;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "unit_price", nullable = false, precision = 17, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "total_amount", nullable = false, precision = 17, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "other_expenses", precision = 17, scale = 2)
    private BigDecimal otherExpenses;
    
    @Column(name = "remaining_quantity", nullable = false)
    private Integer remainingQuantity;
    
    @Column(precision = 6, scale = 4)
    private BigDecimal discount;
    
    @Column(name = "discount_amount", precision = 17, scale = 2)
    private BigDecimal discountAmount;
    
    @Column(name = "discount_price", precision = 17, scale = 2)
    private BigDecimal discountPrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_purchase_item_client_id_client_id"))
    private Client client;
    
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
    
    @Column(name = "taxable_amount", precision = 17, scale = 2)
    private BigDecimal taxableAmount;
    
    @Column(name = "total_tax_amount", precision = 17, scale = 2)
    private BigDecimal totalTaxAmount;
    
    @Column(name = "hsn_code")
    private String hsnCode;
} 