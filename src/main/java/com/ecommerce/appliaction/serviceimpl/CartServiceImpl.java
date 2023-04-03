package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.CartDTO;
import com.ecommerce.appliaction.entity.Cart;
import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NotFoundException;
import com.ecommerce.appliaction.repositotry.CartRepository;
import com.ecommerce.appliaction.repositotry.CustomerRepository;
import com.ecommerce.appliaction.repositotry.ProductRepository;
import com.ecommerce.appliaction.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;


    @Override
    public void addItem(CartDTO cartDTO) throws NotFoundException {
        Cart cart = new Cart();

        Optional<Customer> customer = customerRepository.findById(cartDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found for id: " + cartDTO.getCustomerId());
        }
        cart.setCustomer(customer.get());

        Optional<Product> product = productRepository.findById(cartDTO.getProductId());
        if (product.isEmpty()) {
            throw new NotFoundException("Product not found for id: " + cartDTO.getProductId());
        }
        cart.setProductName(product.get().getProductName());
        cart.setPrice(product.get().getProductPrice());
        cart.setQuantity(cartDTO.getQuantity());

        cartRepository.save(cart);

    }


    @Override
    public Cart updateItem(CartDTO cartDTO, Long id) throws NegativeValueException, NotFoundException {
        var product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found for id: " + cartDTO.getProductId()));

        var customer = customerRepository.findById(cartDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found for id: " + cartDTO.getCustomerId()));

        if (cartDTO.getQuantity() <= 0) {
            throw new NegativeValueException("Invalid quantity entered: " + cartDTO.getQuantity());
        }

        var cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart item not found for id: " + id));

        cart.setQuantity(cartDTO.getQuantity());
        cart.setProductName(product.getProductName());
        cart.setPrice(product.getProductPrice());
        cart.setCustomer(customer);

        cartRepository.save(cart);
        return cart;
    }

    public String removeItem(Long id) throws NotFoundException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found for id = " + id));
        cartRepository.delete(cart);
        return "Cart removed from table";
    }


    @Override
    public Optional<Cart> getCartByID(Long id) throws NotFoundException {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new NotFoundException("Cart item not found for id: " + id);
        }
        return cart;
    }

}

