package com.ecommerce.appliaction.repositotry;

import com.ecommerce.appliaction.entity.Customer;
import com.ecommerce.appliaction.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllOrderByCustomerId(long customer_id);
    List<Order> findAllOrderByDeliveryAddress(String address);


}
