package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CartDTO {

   
    private long quantity;
   
    private long productId;
   
    private long customerId;


}
