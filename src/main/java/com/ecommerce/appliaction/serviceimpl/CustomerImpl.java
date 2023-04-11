package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.CustomerDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void create(CustomerDTO customerDTO) throws AlreadyExists {
        log.info("customer serviceImpl  :: create ");
        Customer customer = new Customer();
         Optional<Customer> customer2 = Optional.ofNullable(customerRepository.findByEmail(customerDTO.getEmail()));
        if (customer2.isPresent()) {
            throw new AlreadyExists("Email already exists please change the Email : " + customerDTO.getEmail());
        } else {
            customer.setCustomerName(customerDTO.getCustomerName());
            customer.setEmail(customerDTO.getEmail());
            customer.setAddress(customerDTO.getAddress());
            log.info("customer saved in database successfully ");
            customerRepository.save(customer);


        }

    }

    @Override
    public CustomerDTO update(CustomerDTO customerDto, Long id) throws NoSuchElementFoundException {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Customer not found  id : " + id));
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());


        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerName(savedCustomer.getCustomerName());
        customerDTO.setAddress(savedCustomer.getAddress());
        customerDTO.setEmail(savedCustomer.getEmail());

        return customerDTO;
    }


    @Override
    public void delete(Long id) throws NoSuchElementFoundException {
        var customer1 = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("customer not found id : " + id));
        customerRepository.deleteById(customer1.getId());

    }


    @Override
    public Customer getById(Long id) throws NoSuchElementFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Customer  not found for id = " + id));
    }

    @Override
    public List<Customer> getAll() throws EmptyDataException {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new EmptyDataException("no such element found,table is empty", HttpStatus.NOT_FOUND);
        }
        return customerList;
    }


}
