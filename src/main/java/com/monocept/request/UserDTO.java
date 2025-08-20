package com.monocept.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDTO(

        @NotBlank(message = "UserName is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Role is required")
        @Pattern(regexp = "ADMIN|SALES_USER", message = "Role must be either ADMIN or SALES_USER")
        String role

) {}
