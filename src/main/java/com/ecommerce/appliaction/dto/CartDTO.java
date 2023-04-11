package com.ecommerce.appliaction.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartDTO {

    @NotNull(message = "quantity shouldn't be null")
    private long quantity;
    @NotNull(message = "productID shouldn't be null")
    private long productId;
    @NotNull(message = "customerId shouldn't be null")
    private long customerId;


}
