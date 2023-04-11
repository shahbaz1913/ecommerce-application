package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.CustomerDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.service.CustomerService;
import com.ecommerce.appliaction.serviceimpl.CustomerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer-api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/customer")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerDTO customerDto) throws AlreadyExists {
        customerService.create(customerDto);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }


    @PutMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO, @PathVariable long id) throws NoSuchElementFoundException {
        customerService.update(customerDTO, id);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) throws NoSuchElementFoundException {
        this.customerService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws NoSuchElementFoundException {
        Customer customer = customerService.getById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/allCustomer")
    public ResponseEntity<List<Customer>> getAllCustomer() throws EmptyDataException {
        List<Customer> customerList = customerService.getAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }




}
