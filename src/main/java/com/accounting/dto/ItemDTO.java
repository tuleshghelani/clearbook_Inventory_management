package com.accounting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDTO {
    private Long id;
    private String name;
    private String code;
    private Long categoryId;
    private String description;
    private String unit;
    private String hsnCode;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private BigDecimal minimumStock;
    private BigDecimal currentStock;
    private String status;
    private String categoryName;
} 