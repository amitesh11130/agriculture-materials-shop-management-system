package com.monocept.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(

        @NotNull(message = "Meta information is required")
        @Valid
        Meta meta,
        Object data,
        Object error

) {
}
