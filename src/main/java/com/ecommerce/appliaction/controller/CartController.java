package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.entity.Cart;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cart-api")
public class    CartController {
    @Autowired
    CartService cartService;
    @PostMapping("/cart")
    public ResponseEntity<String> addToCart(@RequestBody @Valid CartDTO cartDTO) throws NoSuchElementFoundException {
        cartService.addItem(cartDTO);
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }



    @PutMapping("/cart/{id}")
    public ResponseEntity<String> updateCart(@RequestBody @Valid CartDTO cartDTO, @PathVariable long id) throws NoSuchElementFoundException, NegativeValueException {
        cartService.updateItem(cartDTO, id);
        return new ResponseEntity<>("Cart updated successfully", HttpStatus.CREATED);
    }


    @Operation(description = "Delete cart")
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) throws NoSuchElementFoundException {
        cartService.removeItem(id);
        return new ResponseEntity<>("Message : category deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/cart/{id}")
    public Cart getCategory(@PathVariable Long id) throws NoSuchElementFoundException {
        Cart cart = this.cartService.getCartByID(id);
        return cart;
    }



    @ResponseBody
    @GetMapping("/allCart")
    public ResponseEntity<List<Cart>> getAllCart() throws EmptyDataException {
        List<Cart> all = cartService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
