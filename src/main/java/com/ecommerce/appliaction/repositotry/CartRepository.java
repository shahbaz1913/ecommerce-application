package com.ecommerce.appliaction.repositotry;

 import com.ecommerce.appliaction.entity.Cart;
 import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
