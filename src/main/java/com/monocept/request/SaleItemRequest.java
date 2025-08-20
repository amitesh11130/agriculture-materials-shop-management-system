package com.monocept.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleItemRequest(

        @NotNull(message = "Product ID cannot be null")
        Long productId,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be positive")
        Long quantity

//        @NotNull(message = "Price per unit cannot be null")
//        @Positive(message = "Price per unit must be greater than 0")
//        Double pricePerUnit

) {}
