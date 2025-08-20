package com.monocept.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"customer", "items", "payments"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "sale_table")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @Column(nullable = false)
    private OffsetDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SaleItem> items = new ArrayList<>();


    @Column(nullable = false)
    private Double subTotal;

    private Double discountAmount; // absolute
    private Double taxAmount; // absolute

    @Column(nullable = false)
    private Double totalAmount; // subTotal - discount + tax

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Payment> payments = new ArrayList<>();

}
