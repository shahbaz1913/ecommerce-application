package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.repositotry.OrderRepository;
import com.ecommerce.appliaction.repositotry.ProductRepository;
import com.ecommerce.appliaction.service.OrderService;
import com.ecommerce.appliaction.dto.OrderDTO;
import com.ecommerce.appliaction.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerRepository customerRepository;


    @Override
    public void create(OrderDTO orderDTO) throws NotFoundException, NegativeValueException {
        Order newOrder = new Order();

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found for ID: " + orderDTO.getCustomerId()));
        newOrder.setCustomerName(customer.getCustomerName());
        newOrder.setCustomer(customer);

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found for ID: " + orderDTO.getProductId()));
        if (orderDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity for order: " + orderDTO.getQuantity());
        }
        newOrder.setPrice(product.getProductPrice());

        double totalCost = (orderDTO.getQuantity() * product.getProductPrice()) - product.getProductDiscount();
        newOrder.setTotalCost(totalCost);

        newOrder.setQuantity(orderDTO.getQuantity());
        newOrder.setOrderAddress(orderDTO.getOrderAddress());

        newOrder.setProducts(new ArrayList<>());

        orderRepository.save(newOrder);
    }

    @Override
    public Order update(OrderDTO orderDTO, Long id) throws NotFoundException, NegativeValueException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found for ID: " + id));
        if (orderDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity for order: " + orderDTO.getQuantity());
        }
        order.setQuantity(orderDTO.getQuantity());

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found for ID: " + orderDTO.getCustomerId()));
        order.setCustomerName(customer.getCustomerName());
        order.setCustomer(customer);

        Product orderedProduct = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found for ID: " + orderDTO.getProductId()));
        order.setPrice(orderedProduct.getProductPrice());

        double totalCost = (orderedProduct.getProductPrice() * orderDTO.getQuantity()) - orderedProduct.getProductDiscount();
        order.setTotalCost(totalCost);

        order.setOrderAddress(orderDTO.getOrderAddress());

        return orderRepository.save(order);
    }


    @Override
    public void delete(Long id) throws NotFoundException {
        orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found for order ID: " + id));
        orderRepository.deleteById(id);
    }

    @Override
    public Order getById(Long id) throws NotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found for order ID: " + id));
    }


    @Override
    public List<Order> getAll() throws EmptyDataException {
        List<Order> orders = this.orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new EmptyDataException("Table is empty", HttpStatus.NO_CONTENT);
        }
        return orders;
    }

}
