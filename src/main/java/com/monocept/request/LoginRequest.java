package com.monocept.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "UserName is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
}
