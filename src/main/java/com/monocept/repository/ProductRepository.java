package com.monocept.repository;

import com.monocept.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByQuantityInStockLessThanEqual(Long quantityInStock);
}
