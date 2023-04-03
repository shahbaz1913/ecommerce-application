package com.ecommerce.appliaction.controller;

import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.service.OrderService;
import com.ecommerce.appliaction.dto.OrderDTO;
import com.ecommerce.appliaction.entity.Order;
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

    @PostMapping("/addNewOrder")
    public ResponseEntity<String> addOrder(@RequestBody @Valid OrderDTO orderDTO) throws NotFoundException, NegativeValueException {
        orderService.create(orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateOrderBy/{id}")
    public ResponseEntity<String> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable long id) throws NotFoundException, NegativeValueException {
        this.orderService.update(orderDTO, id);
        return new ResponseEntity<>(" order update successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteOrderBy/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) throws NotFoundException {
        orderService.delete(id);
        return new ResponseEntity<>(" deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/fetchOrderBy/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) throws NotFoundException {
        Order order = orderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/fetchAllOrder")
    public ResponseEntity<List<Order>> getAllOrder() throws EmptyDataException {
        List<Order> all = orderService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
