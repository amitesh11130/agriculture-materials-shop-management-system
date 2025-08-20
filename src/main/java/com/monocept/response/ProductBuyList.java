package com.monocept.response;

public record ProductBuyList(
        String name,
        Long quantity,
        Double pricePerUnit,
        Double totalQuantityPrice

) {
}
