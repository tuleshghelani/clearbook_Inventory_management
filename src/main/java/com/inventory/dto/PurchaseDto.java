package com.inventory.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.inventory.config.CustomDateDeserializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseDto {
    private Long id;
    private Long productId;
    private Long categoryId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private Long clientId;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "IST")
    private OffsetDateTime purchaseDate;
    private String invoiceNumber;
    private BigDecimal otherExpenses;
    private Integer currentPage;
    private Integer perPageRecord;
    private String search;
    private String status;
    private BigDecimal discount;
    private BigDecimal discountAmount;
    private BigDecimal discountPrice;
    private List<PurchaseItemDto> items;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private String remarks;
    private Long customerId;
    private BigDecimal totalOtherExpenses;
    private BigDecimal netAmount;
    private String paymentStatus;
    private String paymentMode;
    private String paymentReference;
    // Search parameters
    private String sortBy;
    private String sortOrder;
}