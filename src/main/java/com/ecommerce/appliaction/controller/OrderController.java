package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.dto.OrderDTO;
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
        List<Order> orders = orderService.findByCustomerId(id);
        return orders;
    }


    @PutMapping("/order/{id}")
    public ResponseEntity<String> updateOrder(@RequestBody @Valid OrderDTO orderDTO, @PathVariable long id) throws NoSuchElementFoundException, NegativeValueException {
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
    @GetMapping("/address")
    public ResponseEntity<List<Order>>  getAllOrderByAddress(@PathVariable  String address){
        List<Order> orders=orderService.findByDeliveryAddress(address);
        return ResponseEntity.ok(orders);
    }

}
