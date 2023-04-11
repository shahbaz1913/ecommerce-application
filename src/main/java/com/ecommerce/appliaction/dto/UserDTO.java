package com.ecommerce.appliaction.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserDTO {
    @NotNull(message = "username shouldn't be null")
    private String username;
    @NotNull(message = "password shouldn't be null")
    private String password;
}
