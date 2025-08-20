package com.monocept.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(
        Double amount,

        @NotBlank(message = "Role is required")
        @Pattern(regexp = "CASH|UPI|CARD", message = "Method must be either CASH,UPI Or CARD")
        String method
) {
}
