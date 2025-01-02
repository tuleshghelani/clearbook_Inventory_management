package com.accounting.entity;

import java.time.OffsetDateTime;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client extends BaseEntity {
    
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone", length = 32)
    private String phone;
    
    @Column(name = "email", length = 256)
    private String email;
    
    @Column(name = "status", nullable = false, length = 2)
    private String status = "A";
    
    @Column(name = "updated_at", length = 29, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}