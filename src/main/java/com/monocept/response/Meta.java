package com.monocept.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Meta(

        @NotNull(message = "Code is required")
        int code,

        @NotNull(message = "Status is required")
        boolean status,

        @NotBlank(message = "Description is required")
        String description

) {}
