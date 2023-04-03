package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {
    private long customerId;
    private long productId;
    private String orderAddress;
    private int quantity;
}
