package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.OrderDTO;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Order;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.repositotry.OrderRepository;
import com.ecommerce.appliaction.repositotry.ProductRepository;
import com.ecommerce.appliaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void create(OrderDTO orderDTO) throws NoSuchElementFoundException, NegativeValueException {
        Order newOrder = new Order();

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new NoSuchElementFoundException("Customer not found for ID: " + orderDTO.getCustomerId()));
        newOrder.setCustomerName(customer.getCustomerName());
        newOrder.setCustomer(customer);

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException("Product not found for ID: " + orderDTO.getProductId()));
        if (orderDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity for order: " + orderDTO.getQuantity());
        }
        newOrder.setProductName(product.getProductName());
        newOrder.setPrice(product.getProductPrice());

        double totalCost = (orderDTO.getQuantity() * product.getProductPrice()) - product.getProductDiscount();
        newOrder.setTotalCost(totalCost);

        List<Product> newProduct = new ArrayList<>();
        newProduct.add(product);
        newOrder.setProducts(newProduct);

        newOrder.setQuantity(orderDTO.getQuantity());
        if (orderDTO.getDeliveryAddress().isEmpty()) {
            newOrder.setDeliveryAddress(customer.getAddress());
        } else {
            newOrder.setDeliveryAddress(orderDTO.getDeliveryAddress());
        }
        product.setProductStock(product.getProductStock() - newOrder.getQuantity());

        orderRepository.save(newOrder);
    }

    @Override
    public Order update(OrderDTO orderDTO, Long id) throws NoSuchElementFoundException, NegativeValueException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Order not found for ID: " + id));
        if (orderDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity for order: " + orderDTO.getQuantity());
        }

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new NoSuchElementFoundException("Customer not found for ID: " + orderDTO.getCustomerId()));
        order.setCustomerName(customer.getCustomerName());
        order.setCustomer(customer);

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException("Product not found for ID: " + orderDTO.getProductId()));
        product.setProductStock(product.getProductStock() - orderDTO.getQuantity());
        order.setPrice(product.getProductPrice());
        double totalCost = (product.getProductPrice() * orderDTO.getQuantity()) - product.getProductDiscount();
        order.setTotalCost(totalCost);
        order.setQuantity(orderDTO.getQuantity());
        List<Product> newProduct = new ArrayList<>();
        newProduct.add(product);
        order.setProducts(newProduct);

        if (orderDTO.getDeliveryAddress().isEmpty()) {
            order.setDeliveryAddress(customer.getAddress());
        } else {
            order.setDeliveryAddress(orderDTO.getDeliveryAddress());
        }

        return orderRepository.save(order);
    }


    @Override
    public void delete(Long id) throws NoSuchElementFoundException {
        orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Order not found for order ID: " + id));
        orderRepository.deleteById(id);
    }

    @Override
    public Order getById(Long id) throws NoSuchElementFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Order not found for order ID: " + id));
    }

    @Override
    public List<Order> findByCustomerId(long id) {
       List<Order> orders= orderRepository.findAllOrderByCustomerId(id);
        return orders;
    }

    @Override
    public List<Order> findByDeliveryAddress(String address) {
        List<Order> orders=findByDeliveryAddress(address);
        return orders;
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
