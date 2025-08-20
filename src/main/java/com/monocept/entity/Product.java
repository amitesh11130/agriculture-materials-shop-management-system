package com.monocept.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "product_table")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(unique = true, nullable = false)
    private String name;
    private String category;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Long quantityInStock;
    private String supplierName;
    private String supplierContact;

}
