package com.monocept.repository;

import com.monocept.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByNameAndContactNumber(String name, String contactNumber);

    @EntityGraph(attributePaths = {"sales"})
    Optional<Customer> findWithSalesByCustomerId(Long customerId);
}

