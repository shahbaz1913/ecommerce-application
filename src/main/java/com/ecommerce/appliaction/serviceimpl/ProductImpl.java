package com.ecommerce.appliaction.serviceimpl;

import com.ecommerce.appliaction.dto.ProductDTO;
import com.ecommerce.appliaction.entity.Category;
import com.ecommerce.appliaction.entity.Product;
import com.ecommerce.appliaction.exception.AlreadyExists;
import com.ecommerce.appliaction.exception.EmptyDataException;
import com.ecommerce.appliaction.exception.NegativeValueException;
import com.ecommerce.appliaction.exception.NoSuchElementFoundException;
import com.ecommerce.appliaction.repositotry.CategoryRepository;
import com.ecommerce.appliaction.repositotry.ProductRepository;
import com.ecommerce.appliaction.service.ProductService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ProductImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void create(ProductDTO productDTO) throws AlreadyExists, NegativeValueException {
        //validate ProductDTO
        log.info("productService : create method         ");

        validateProductDTO(productDTO);


        var productName = productRepository.findByProductName(productDTO.getProductName());
        Optional<Product> product1= Optional.ofNullable(productRepository
                .findByProductName(productDTO.getProductName()));
        if (product1.isPresent()) {
            log.error("product is already exists");
            throw new AlreadyExists("Product is already present in the table please change the product name: " + productDTO.getProductName());
        }else {
            log.info("creating new product productName"+productDTO.getProductName());

            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setProductPrice(productDTO.getProductPrice());
            product.setProductDiscount(productDTO.getProductDiscount());
            product.setProductStock(productDTO.getProductStock());
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NoSuchElementFoundException("Category not found please change the categoryId: " + productDTO.getCategoryId()));
            product.setCategory(category);
            log.info("Created successfully");
            productRepository.save(product);
        }
    }

    @Override
    public Product update(ProductDTO productDTO, Long id) throws NoSuchElementFoundException, NegativeValueException {
        //validate ProductDTO
        validateProductDTO(productDTO);

        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Product not found id = " + id));


        existingProduct.setProductStock(productDTO.getProductStock());
        existingProduct.setProductDiscount(productDTO.getProductDiscount());
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setProductPrice(productDTO.getProductPrice());
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementFoundException("Category not found please change the categoryId: " + productDTO.getCategoryId()));
        existingProduct.setCategory(category);


        return productRepository.save(existingProduct);
    }


    @Override
    public void delete(Long id) throws NoSuchElementFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Product not found for id = " + id));
        productRepository.delete(product);
    }


    @Override
    public Product getById(Long id) throws NoSuchElementFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("please enter valid product id = " + id));
    }


    @Override
    public List<Product> getAll() throws EmptyDataException {
        List<Product> productList = this.productRepository.findAll();
        if (productList.isEmpty()) {
            throw new EmptyDataException("No products found", HttpStatus.NOT_FOUND);
        }
        return productList;
    }


    private void validateProductDTO(ProductDTO productDTO) throws NegativeValueException {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }
        if (StringUtils.isBlank(productDTO.getProductName())) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
        if (productDTO.getProductStock() < 0) {
            throw new NegativeValueException("Product stock cannot be negative: " + productDTO.getProductStock());
        }
        if (productDTO.getProductPrice() <= 0) {
            throw new NegativeValueException("Product price must be positive: " + productDTO.getProductPrice());
        }
        if (productDTO.getProductDiscount() < 0) {
            throw new NegativeValueException("Product discount cannot be negative: " + productDTO.getProductDiscount());
        }
    }

}
