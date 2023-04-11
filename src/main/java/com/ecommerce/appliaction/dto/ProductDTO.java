package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProductDTO {

    @NotNull(message = "productName shouldn't be null")
    private String productName;
    @NotNull(message = "productPrice shouldn't be null")
    private double productPrice;
    @NotNull(message = "productStock shouldn't be null")
    private int productStock;

    private double productDiscount;
    @NotNull(message = "categoryId shouldn't be null")
    private long categoryId;
}
