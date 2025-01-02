package com.accounting.entity;

import com.accounting.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_categories", indexes = {
    @Index(name = "idx_item_category_code", columnList = "code"),
    @Index(name = "idx_item_category_client_id", columnList = "client_id")
})
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCategory extends BaseEntity {
    
    @Column(name = "name", nullable = false, length = 128)
    private String name;
    
    @Column(name = "code", nullable = false, length = 32)
    private String code;
    
    @Column(name = "description", length = 512)
    private String description;
    
    @Column(name = "status", nullable = false, length = 2)
    private String status = "A";
} 