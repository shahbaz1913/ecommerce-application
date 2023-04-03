package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.entity.Cart;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.repositotry.CartRepository;
import com.ecommerce.appliaction.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cart-api")
public class CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody @Valid CartDTO cartDTO) throws NotFoundException {
        cartService.addItem(cartDTO);
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }


    @PutMapping("/updateCartBy/{id}")
    public ResponseEntity<String> updateCart(@RequestBody @Valid CartDTO cartDTO, @PathVariable long id) throws NotFoundException, NegativeValueException {
        cartService.updateItem(cartDTO, id);
        return new ResponseEntity<>("Cart updated successfully", HttpStatus.CREATED);
    }


    @Operation(description = "Delete cart")
    @DeleteMapping("/deleteCartBy/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) throws NotFoundException {
        cartService.removeItem(id);
        return new ResponseEntity<>("Message : category deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/fetchCartBy/{id}")
    public Optional<Cart> getCategory(@PathVariable Long id) throws NotFoundException {
        Optional<Cart> cart = this.cartService.getCartByID(id);
        return cart;
    }




}
