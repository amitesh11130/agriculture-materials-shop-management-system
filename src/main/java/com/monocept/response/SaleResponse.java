package com.monocept.response;

import java.time.OffsetDateTime;
import java.util.List;

public record SaleResponse(
        Long saleId,
        OffsetDateTime date,
        String customerName,
        List<ProductBuyList> product,
        Double subTotal,
        Double discountAmount,
        Double taxAmount,
        Double totalAmount,
        Double amountPaid,
        Double pendingAmount,
        String receiptKey

) {
}
