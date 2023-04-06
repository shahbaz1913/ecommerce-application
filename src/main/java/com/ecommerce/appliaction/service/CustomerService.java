package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.CustomerDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
        void create(CustomerDTO customerDTO) throws AlreadyExists;
        CustomerDTO update(CustomerDTO customer, Long id) throws NoSuchElementFoundException;
        void delete(Long id);
        Customer getById(Long id);
        List<Customer> getAll() throws EmptyDataException;

}
