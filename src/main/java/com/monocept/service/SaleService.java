package com.monocept.service;

import com.monocept.entity.Sale;
import com.monocept.request.SaleRequest;
import com.monocept.request.SaleResponseDTO;
import com.monocept.response.SaleResponse;

import java.util.List;

public interface SaleService {

    SaleResponse createSale(SaleRequest saleRequest);

    SaleResponseDTO getSaleById(Long saleId);

    List<SaleResponseDTO> getAllSales();

//    Sale updateSale(Long saleId, SaleRequest saleRequest);


    List<Sale> findByCustomerCustomerId(Long customerId);
}
