package com.accounting.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AccountDTO {
    private Long id;
    private String code;
    private String name;
    private String type;
    private String groupName;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private String status;
} 