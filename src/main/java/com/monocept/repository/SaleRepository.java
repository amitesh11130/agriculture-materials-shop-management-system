package com.monocept.repository;

import com.monocept.entity.Sale;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

//    @EntityGraph(attributePaths = {"items", "payments"})
    List<Sale> findByCustomerCustomerId(Long customerId);
}
