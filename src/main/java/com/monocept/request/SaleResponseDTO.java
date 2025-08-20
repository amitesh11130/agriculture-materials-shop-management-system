package com.monocept.request;

import java.util.List;

public record SaleResponseDTO(
        Long saleId,
        String date,
        Double subTotal,
        Double discountAmount,
        Double taxAmount,
        Double totalAmount,
        List<SaleItemDTO> items
) {}