//package com.monocept.service.impl;
//
//import com.monocept.entity.SaleItem;
//import com.monocept.repository.SaleItemRepository;
//import com.monocept.service.SaleItemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//public class SaleItemServiceImpl implements SaleItemService {
//
//    private final SaleItemRepository saleItemRepository;
//
//    @Override
//    public SaleItem getSaleItemById(Long saleItemId) {
//        return saleItemRepository.findById(saleItemId)
//                .orElseThrow(() -> new RuntimeException("SaleItem not found with ID: " + saleItemId));
//    }
//}
