package com.ecommerce.appliaction.service;

import com.ecommerce.appliaction.dto.OrderDTO;
import com.ecommerce.appliaction.entity.Order;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    void create(OrderDTO orderDTO)throws NotFoundException, NegativeValueException;

    Order update(OrderDTO orderDTO, Long id) throws NegativeValueException;

    void delete(Long id);

    Order getById(Long id);

    List<Order> getAll() throws EmptyDataException;
}
