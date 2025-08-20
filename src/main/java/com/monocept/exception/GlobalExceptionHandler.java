package com.monocept.exception;

import com.monocept.response.ApiResponse;
import com.monocept.response.Meta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation failed for request: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String defaultMessage = fieldError.getDefaultMessage();
            errors.put(fieldName, defaultMessage);
        });
        log.warn("Validation errors: {}", errors);
        var meta = new Meta(HttpStatus.BAD_REQUEST.value(), false, "Request validation failed due to invalid or missing fields");
        return new ApiResponse(meta, null, errors);
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInsufficientStock(InsufficientStockException ex) {
        var meta = new Meta(HttpStatus.BAD_REQUEST.value(), false, "Insufficient stock available for the requested product");
        return new ApiResponse(meta, null, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handleNotFound(ResourceNotFoundException ex) {
        var meta = new Meta(HttpStatus.NOT_FOUND.value(), false, "Requested resource was not found");
        return new ApiResponse(meta, null, ex.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidField(InvalidFieldException ex) {
        var meta = new Meta(HttpStatus.BAD_REQUEST.value(), false, "Invalid field in request body");
        return new ApiResponse(meta, null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse handleGenericException(Exception ex) {
        Meta meta = new Meta(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, "Internal server error");
        return new ApiResponse(meta, null, ex.getMessage());
    }

}
