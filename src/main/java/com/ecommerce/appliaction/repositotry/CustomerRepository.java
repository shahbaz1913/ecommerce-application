package com.ecommerce.appliaction.repositotry;

import com.ecommerce.appliaction.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByEmail(String email);

 /*  @Query("SELECT c FROM Customer c WHERE c.customerName = :customerName AND (c.email IS NULL OR c.email = '')")
   List<Customer> findByCustomerNameAndEmail(@Param("") String customerName);*/




}
