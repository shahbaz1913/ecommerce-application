package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.CustomerDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void create(CustomerDTO customerDTO) throws AlreadyExists {
        Customer customer = new Customer();
        Customer customer1 = customerRepository.findByEmail(customerDTO.getEmail());
        if (customer1 == null) {
            customer1.setCustomerName(customerDTO.getCustomerName());
            customer1.setEmail(customerDTO.getEmail());
            customer1.setPassword(customerDTO.getPassword());
            customerRepository.save(customer1);

        } else {
            throw new AlreadyExists("Email already exists please change the Email : " + customerDTO.getEmail());
        }

    }

    @Override
    public CustomerDTO update(CustomerDTO customerDto, Long id) throws NotFoundException {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found  id :" + id));
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setPassword(customerDto.getPassword());
        customer.setEmail(customerDto.getEmail());

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = modelMapper.map(savedCustomer, CustomerDTO.class);
        return customerDTO;


       /* var customer1 = customerRepository.findById(id).get();
        customer1.setCustomerName(customer.getCustomerName());
        customer1.setEmail(customer.getEmail());
        customer1.setPassword(customer.getPassword());*/

       /* CustomerDTO createdCustomerDto = new CustomerDTO();
        createdCustomerDto.setCustomerName(savedCustomer.getCustomerName());
        createdCustomerDto.setPassword(savedCustomer.getPassword());
        createdCustomerDto.setEmail(savedCustomer.getEmail());
        return createdCustomerDto;*/

    }

    @Override
    public void delete(Long id) throws NotFoundException {
        var customer1 = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer not found id : " + id));
        customerRepository.deleteById(customer1.getId());

    }


    @Override
    public Customer getById(Long id) throws NotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer  not found for id = " + id));
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
