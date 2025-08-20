package com.monocept.controller;


import com.monocept.entity.User;
import com.monocept.exception.ResourceNotFoundException;
import com.monocept.repository.UserRepository;
import com.monocept.request.SaleRequest;
import com.monocept.request.SaleResponseDTO;
import com.monocept.response.ApiResponse;
import com.monocept.response.Meta;
import com.monocept.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sale")
public class SaleController {


    private final SaleService saleService;
    private final UserRepository userRepository;

    @PostMapping("/create")
    public ApiResponse createSale(@Valid @RequestBody SaleRequest saleRequest/*, Authentication authentication*/) {

//        String name = authentication.getName();
//        Optional<User> byUsername = userRepository.findByUsername(name);
//        Long userId = byUsername.get().getUserId();

        try {
            var sale = saleService.createSale(saleRequest);
            Meta meta = new Meta(HttpStatus.CREATED.value(), true, "Sale created successfully");
            return new ApiResponse(meta, sale, null);
        } catch (Exception e) {
            Meta meta = new Meta(HttpStatus.BAD_REQUEST.value(), false, e.getMessage());
            return new ApiResponse(meta, null, null);
        }
    }

    @GetMapping("/getAll")
    public ApiResponse getAllSales() {
        List<SaleResponseDTO> allSales = saleService.getAllSales();
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Fetched all sales");
        return new ApiResponse(meta, allSales, null);
    }

    @GetMapping("/getById")
    public ApiResponse getSaleBYId(@RequestHeader Long saleId) {
        SaleResponseDTO saleById = saleService.getSaleById(saleId);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Fetched sale by Id");
        return new ApiResponse(meta, saleById, null);
    }
}

