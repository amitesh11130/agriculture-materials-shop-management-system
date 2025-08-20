package com.monocept.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "sales")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer_table")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contactNumber;
    private String address;
    private Double pendingAmount;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Sale> sales = new ArrayList<>();

}
