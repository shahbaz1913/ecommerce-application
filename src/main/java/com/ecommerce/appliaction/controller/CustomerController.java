package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.CustomerDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NotFoundException;
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


    @PostMapping("/addNewCustomer")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerDTO customerDto) throws AlreadyExists {
        customerService.create(customerDto);
        return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
    }


    @PutMapping("/updateCustomerBy/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO, @PathVariable long id) throws NotFoundException {
        customerService.update(customerDTO, id);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCustomerBy/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) throws NotFoundException {
        this.customerService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/fetchCustomerBy/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) throws NotFoundException {
        Customer customer = customerService.getById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/fetchAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomer() throws EmptyDataException {
        List<Customer> customerList = customerService.getAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }
    @Autowired
    CustomerImpl customer;






}
