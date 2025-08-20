package com.monocept.request;

import jakarta.validation.constraints.*;

public record CustomerDTO(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Contact number is required")
        String contactNumber,

        @NotBlank(message = "Address is required")
        String address,

        @NotNull(message = "Pending amount is required")
        @PositiveOrZero(message = "Pending amount must be zero or positive")
        Double pendingAmount

) {}
