package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.entity.Cart;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CartRepository;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.repositotry.ProductRepository;
import com.ecommerce.appliaction.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;


    @Override
    public void addItem(CartDTO cartDTO) throws NoSuchElementFoundException {
        log.info("CartServiceImpl :: addItem :: method called");
        Cart cart = new Cart();

        Optional<Customer> customer = customerRepository.findById(cartDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new NoSuchElementFoundException("Customer not found for id: " + cartDTO.getCustomerId());
        }
        cart.setCustomer(customer.get());
        cart.setCustomerName(customer.get().getCustomerName());
        log.warn("cartServiceImpl : addItem request parameters {}", customer.get().getCustomerName());

        Optional<Product> product = productRepository.findById(cartDTO.getProductId());
        if (product.isEmpty()) {
            throw new NoSuchElementFoundException("Product not found for id: " + cartDTO.getProductId());
        }
        cart.setProductName(product.get().getProductName());
        cart.setPrice(product.get().getProductPrice());
        cart.setQuantity(cartDTO.getQuantity());
        log.info("cart saved in database ");
        cartRepository.save(cart);

    }


    @Override
    public Cart updateItem(CartDTO cartDTO, Long id) throws NegativeValueException, NoSuchElementFoundException {
        var product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementFoundException("Product not found for id: " + cartDTO.getProductId()));

        var customer = customerRepository.findById(cartDTO.getCustomerId())
                .orElseThrow(() -> new NoSuchElementFoundException("Customer not found for id: " + cartDTO.getCustomerId()));

        var cart = cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Cart item not found for id: " +  id));

        if (cartDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity entered: " + cartDTO.getQuantity());
        }
        cart.setCustomerName(customer.getCustomerName());
        cart.setQuantity(cartDTO.getQuantity());
        cart.setProductName(product.getProductName());
        cart.setPrice(product.getProductPrice());
        cart.setCustomer(customer);

        cartRepository.save(cart);
        return cart;
    }

    public String removeItem(Long id) throws NoSuchElementFoundException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Cart not found for id = " + id));
        cartRepository.delete(cart);
        return "Cart removed from table";
    }


    @Override
    public Cart getCartByID(Long id) throws NoSuchElementFoundException {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("cart not found  id : " + id));


        /* Cart cart = new Cart();
        try {
            log.info("CartServiceImpl : getById execution started");
            cart = cartRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementFoundException("Cart not found with id " + id, id));
            log.debug("retrieving  data from database for id {}", id);

        } catch (Exception e) {
            log.error("exception occurred while retrieving");
            throw new NoSuchElementFoundException("exception occurred while retrieving {} from database,Exception message {}",id);
        }
        log.info("execution ended");
        return cart;*/

    }

    @Override
    public List<Cart> getAll() throws EmptyDataException {
        List<Cart> cartList = cartRepository.findAll();
        if (cartList.isEmpty()) {
            throw new EmptyDataException("no such element found,table is empty", HttpStatus.NOT_FOUND);
        }
        return cartList;
    }

}

