package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.entity.Cart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CartService {

    void addItem(CartDTO cartDTO) throws NoSuchElementFoundException;

    Cart updateItem(CartDTO cartDTO, Long id) throws NegativeValueException;

    String removeItem(Long id);

    Cart getCartByID(Long id);
    List<Cart> getAll()throws EmptyDataException;

}
