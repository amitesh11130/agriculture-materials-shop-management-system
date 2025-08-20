package com.monocept.controller;

import com.monocept.entity.Customer;
import com.monocept.response.ApiResponse;
import com.monocept.response.CustomerResponse;
import com.monocept.response.Meta;
import com.monocept.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/getAll")
    public ApiResponse customerHistory() {
        List<CustomerResponse> allCustomers = customerService.getAllCustomers();
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Fetch all customers");
        return new ApiResponse(meta, allCustomers, null);
    }

    @GetMapping(value = "/getById")
    public ApiResponse customerHistory(@RequestHeader Long customerId) {
        var customerById = customerService.getCustomerById(customerId);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Fetch customer by id");
        return new ApiResponse(meta, customerById, null);
    }

    @PatchMapping(value = "/update/{customerId}")
    public ApiResponse updateCustomer(
            @PathVariable Long customerId,
            @RequestBody Map<String, Object> updates) {
        Customer updated = customerService.updateCustomer(customerId, updates);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Update customer successfully");
        return new ApiResponse(meta, updated, null);
    }
}
