package com.inventory.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PurchaseItemDto {
    private Long id;
    private Long productId;
    private Long categoryId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal otherExpenses;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    private BigDecimal discountPrice;
} 