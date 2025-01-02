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
public class StockMovementDTO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemCode;
    private String referenceType;
    private Long referenceId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String remarks;
    private String status;
} 