package com.monocept.response;

import java.util.List;

public record CustomerResponse(
        String name,
        String contactNumber,
        String address,
        Double pendingAmount,
        Double amountPaid,
        List<ProductBuyList> productBuyLists
) {
}

