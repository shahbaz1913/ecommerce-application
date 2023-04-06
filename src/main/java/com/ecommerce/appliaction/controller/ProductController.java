package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.ProductDTO;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/product-api")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductDTO productDTO) throws AlreadyExists, NegativeValueException {
        productService.create(productDTO);
        return new ResponseEntity<>("Product saved successfully", HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable long id) throws NoSuchElementFoundException, NegativeValueException {
        productService.update(productDTO, id);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) throws NoSuchElementFoundException {
        productService.delete(id);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws NoSuchElementFoundException {
        Product Product = productService.getById(id);
        return new ResponseEntity<>(Product, HttpStatus.OK);
    }


    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProduct() throws EmptyDataException {
        List<Product> all = productService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
