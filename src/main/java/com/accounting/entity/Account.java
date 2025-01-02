package com.accounting.entity;

import java.math.BigDecimal;

import com.accounting.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "group_name")
    private String groupName;
    
    @Column(name = "opening_balance", precision = 17, scale = 2)
    private BigDecimal openingBalance;
    
    @Column(name = "current_balance", precision = 17, scale = 2)
    private BigDecimal currentBalance;
    
    @Column(name = "status")
    private String status = "A";
} 