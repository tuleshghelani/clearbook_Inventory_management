package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "parties")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Party extends BaseEntity {
    
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    
    @Column(name = "code", nullable = false, length = 32)
    private String code;
    
    @Column(name = "address", length = 512)
    private String address;
    
    @Column(name = "phone", length = 32)
    private String phone;
    
    @Column(name = "email", length = 256)
    private String email;
    
    @Column(name = "gst_number", length = 32)
    private String gstNumber;
    
    @Column(name = "pan_number", length = 32)
    private String panNumber;
    
    @Column(name = "opening_balance", precision = 17, scale = 2)
    private BigDecimal openingBalance;
    
    @Column(name = "current_balance", precision = 17, scale = 2)
    private BigDecimal currentBalance;
    
    @Column(name = "credit_limit", precision = 17, scale = 2)
    private BigDecimal creditLimit;
    
    @Column(name = "status", nullable = false, length = 2)
    private String status = "A";
} 