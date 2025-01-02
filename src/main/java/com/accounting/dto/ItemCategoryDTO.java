package com.accounting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCategoryDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String status;
} 