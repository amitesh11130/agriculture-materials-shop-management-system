package com.monocept.controller;

import com.monocept.entity.Product;
import com.monocept.request.ProductDTO;
import com.monocept.response.ApiResponse;
import com.monocept.response.Meta;
import com.monocept.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/create")
    public ApiResponse addProduct(@Valid @RequestBody ProductDTO productDTO) {
        var product = productService.addProduct(productDTO);
        Meta meta = new Meta(HttpStatus.CREATED.value(), true, "Product created successfully");
        return new ApiResponse(meta, product, null);
    }

    @GetMapping(value = "/getAll")
    public ApiResponse getAllProducts() {
        List<Product> products = productService.getAllProducts();
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Fetched all products");
        return new ApiResponse(meta, products, null);
    }

    @GetMapping(value = "/getById")
    public ApiResponse getProductById(@RequestHeader Long id) {
        Product product = productService.getProductById(id);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Product fetched successfully");
        return new ApiResponse(meta, product, null);
    }

    @PutMapping(value = "/updateProduct")
    public ApiResponse updateProduct(@RequestHeader Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        Meta meta = new Meta(HttpStatus.OK.value(), true, "Product updated successfully");
        return new ApiResponse(meta, updatedProduct, null);
    }

    @DeleteMapping(value = "/deleteProduct")
    public ApiResponse deleteProduct(@RequestHeader Long id) {
        boolean deleted = productService.deleteProduct(id);
        String message = deleted ? "Product deleted successfully" : "Product deletion failed";
        Meta meta = new Meta(HttpStatus.OK.value(), deleted, message);
        return new ApiResponse(meta, null, null);
    }
}
