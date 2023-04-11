package com.ecommerce.appliaction.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotNull(message = "categoryName shouldn't be null")
    private String categoryName;
    private String description;
}
