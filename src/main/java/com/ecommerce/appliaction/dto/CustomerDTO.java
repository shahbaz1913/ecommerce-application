package com.ecommerce.appliaction.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerDTO {
    @NotNull(message = "customerName shouldn't be null")
    @NotBlank(message = "customerName shouldn't be blank")
    private String customerName;
    private String address;
    @Email(message = "invalid email address")
    private String email;

}