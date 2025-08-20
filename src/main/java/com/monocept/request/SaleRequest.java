package com.monocept.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record SaleRequest(

        CustomerDTO customerDTO,

        @NotEmpty(message = "Sale must have at least one item")
        @Valid
        List<SaleItemRequest> items,

        @PositiveOrZero(message = "Discount amount must be zero or positive")
        Double discountAmount,

        @PositiveOrZero(message = "Tax amount must be zero or positive")
        Double taxAmount,

        @Valid
        List<PaymentRequest> payments

) {}
