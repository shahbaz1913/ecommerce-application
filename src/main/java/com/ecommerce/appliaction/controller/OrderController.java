package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.OrderDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Order;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.repositotry.OrderRepository;
import com.ecommerce.appliaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/order-api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<String> addOrder(@RequestBody @Valid OrderDTO orderDTO) throws NoSuchElementFoundException, NegativeValueException {
        orderService.create(orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customersOrders/{id}")
    public List<Order> getAllOrderByCustomerId(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        List<Order> order = orderRepository.findAllOrderByCustomerId(customer.get().getId());
        return order;
    }

    @GetMapping("/customersOrders/{name}")
    public List<Order> getAllOrderByCustomerId(@PathVariable String name) {
        List<Order> order = orderRepository.findFirst10ByCustomerNameOrderByIdDesc(name);
        return order;
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<String> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable long id) throws NoSuchElementFoundException, NegativeValueException {
        this.orderService.update(orderDTO, id);
        return new ResponseEntity<>(" order update successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) throws NoSuchElementFoundException {
        orderService.delete(id);
        return new ResponseEntity<>(" deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) throws NoSuchElementFoundException {
        Order order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<Order>> getAllOrder() throws EmptyDataException {
        List<Order> all = orderService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
