package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CartService {

    void addItem(CartDTO cartDTO) throws NotFoundException;

    Cart updateItem(CartDTO cartDTO, Long id) throws NegativeValueException;

    String removeItem(Long id);

    Optional<Cart> getCartByID(Long id);


}
