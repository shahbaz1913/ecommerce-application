package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class OrderDTO {
    @NotNull(message = "customerId shouldn't be null")
    private long customerId;
    @NotNull(message = "productId shouldn't be null")
    private long productId;
    private String deliveryAddress;
    @NotNull(message = "quantity shouldn't be null")
    private int quantity;
}
