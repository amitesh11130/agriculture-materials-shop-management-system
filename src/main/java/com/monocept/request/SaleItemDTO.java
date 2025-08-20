package com.monocept.request;

public record SaleItemDTO(
        String productName,
        Long quantitySold,
        Double pricePerUnit,
        Double totalQuantityPrice
) {}
