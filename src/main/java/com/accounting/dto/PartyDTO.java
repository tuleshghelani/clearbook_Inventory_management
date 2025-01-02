package com.accounting.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartyDTO {
    private Long id;
    private String name;
    private String code;
    private String address;
    private String phone;
    private String email;
    private String gstNumber;
    private String panNumber;
    private BigDecimal openingBalance;
    private BigDecimal currentBalance;
    private BigDecimal creditLimit;
    private String status;
} 