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

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GstSlabDto {
    private Long id;
    private String name;
    private BigDecimal cgstPercentage;
    private BigDecimal sgstPercentage;
    private BigDecimal igstPercentage;
    private String hsnCode;
    private String description;
    
    // Search parameters
    private String search;
    private Integer currentPage;
    private Integer perPageRecord;
    private String sortBy;
    private String sortOrder;
} 