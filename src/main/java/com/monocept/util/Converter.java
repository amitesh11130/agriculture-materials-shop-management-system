package com.monocept.util;

import com.monocept.entity.*;
import com.monocept.request.*;

public class Converter {

    public static User convertUserDtoToEntity(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.username())
                .password(userDTO.password())
                .role(Role.valueOf(userDTO.role()))
                .build();
    }

    public static Product convertProductDtoToEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.name())
                .category(productDTO.category())
                .price(productDTO.price())
                .quantityInStock(productDTO.quantityInStock())
                .supplierName(productDTO.supplierName())
                .supplierContact(productDTO.supplierContact())
                .build();
    }

    public static Sale convertSaleDtoToEntity(SaleRequest saleRequest) {
        return Sale.builder()
                .discountAmount(saleRequest.discountAmount())
                .items(saleRequest.items().stream().map(Converter::convertSaleItemDtoToEntity).toList())
                .customer(convertCustomerDtoToEntity(saleRequest.customerDTO()))
                .taxAmount(saleRequest.taxAmount())
                .payments(saleRequest.payments().stream().map(Converter::convertPaymentDtoToEntity).toList())
                .build();
    }

    private static Payment convertPaymentDtoToEntity(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(paymentRequest.amount())
                .method(ModeOfPayment.valueOf(paymentRequest.method()))
                .build();
    }


    public static SaleItem convertSaleItemDtoToEntity(SaleItemRequest saleItemRequest) {
        return SaleItem.builder()
                .quantitySold(saleItemRequest.quantity())
                .build();
    }

    public static Customer convertCustomerDtoToEntity(CustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.name())
                .contactNumber(customerDTO.contactNumber())
                .address(customerDTO.address())
                .pendingAmount(customerDTO.pendingAmount())
                .build();
    }


}
