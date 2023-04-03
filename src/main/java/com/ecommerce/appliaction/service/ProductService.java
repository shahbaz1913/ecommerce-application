package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.ProductDTO;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void create(ProductDTO productDTO) throws AlreadyExists, NegativeValueException;

    Product update(ProductDTO productDTO, Long id)throws NegativeValueException;

    void delete(Long id);

    Product getById(Long id);

    List<Product> getAll() throws EmptyDataException;
}
