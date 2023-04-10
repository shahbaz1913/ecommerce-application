package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {


    private String productName;
    private double productPrice;
    private int productStock;
    private double productDiscount;
    private long categoryId;
}
