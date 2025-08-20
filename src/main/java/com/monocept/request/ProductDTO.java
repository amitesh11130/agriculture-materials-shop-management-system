package com.monocept.request;

import jakarta.validation.constraints.*;

public record ProductDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be greater than 0")
        Double price,

        @NotNull(message = "Quantity in stock cannot be null")
        @PositiveOrZero(message = "Quantity must be greater or equal to 0")
        Long quantityInStock,

        @NotBlank(message = "Supplier name is required")
        String supplierName,

        @NotBlank(message = "Supplier contact is required")
        String supplierContact

) {}
