package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDTO {


    private long quantity;

    private long productId;

    private long customerId;


}
