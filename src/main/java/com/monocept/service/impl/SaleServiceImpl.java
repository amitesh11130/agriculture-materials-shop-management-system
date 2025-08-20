package com.monocept.service.impl;

import com.monocept.entity.*;
import com.monocept.exception.InsufficientStockException;
import com.monocept.exception.ResourceNotFoundException;
import com.monocept.repository.CustomerRepository;
import com.monocept.repository.PaymentRepository;
import com.monocept.repository.ProductRepository;
import com.monocept.repository.SaleRepository;
import com.monocept.request.*;
import com.monocept.response.ProductBuyList;
import com.monocept.response.SaleResponse;
import com.monocept.service.SaleService;
import com.monocept.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public SaleResponse createSale(SaleRequest saleRequest) {

        Optional<Customer> customerOptional = customerRepository.findByNameAndContactNumber(
                saleRequest.customerDTO().name(), saleRequest.customerDTO().contactNumber());

        Customer customer = customerOptional.orElseGet(() ->
                customerRepository.save(Converter.convertCustomerDtoToEntity(saleRequest.customerDTO()))
        );
        Sale sale = new Sale();
        sale.setDate(OffsetDateTime.now());
        sale.setCustomer(customer);

        List<SaleItem> items = new ArrayList<>();

        for (SaleItemRequest request : saleRequest.items()) {

            Product product = productRepository.findById(request.productId()).orElseThrow(() ->
                    new ResourceNotFoundException("Product not found with ID : " + request.productId()));

            if (product.getQuantityInStock() < request.quantity()) {
                throw new InsufficientStockException(product.getName());
            }
            product.setQuantityInStock(product.getQuantityInStock() - request.quantity());

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setPricePerUnit(product.getPrice());
            saleItem.setQuantitySold(request.quantity());
            saleItem.setTotalQuantityPrice(request.quantity() * product.getPrice());

            items.add(saleItem);
        }
        sale.setItems(items);
        sale.setSubTotal(items.stream().mapToDouble(SaleItem::getTotalQuantityPrice).sum());
        sale.setDiscountAmount(saleRequest.discountAmount());
        sale.setTaxAmount(saleRequest.taxAmount());
        sale.setTotalAmount(sale.getSubTotal() - saleRequest.discountAmount() + saleRequest.taxAmount());

        Sale saved = saleRepository.save(sale);

        Double paidAmount = 0.0;
        if (saleRequest.payments() != null) {
            for (PaymentRequest paymentRequest : saleRequest.payments()) {

                paidAmount += paymentRequest.amount();
                Payment payment = Payment.builder()
                        .amount(paymentRequest.amount())
                        .date(OffsetDateTime.now())
                        .sale(saved)
                        .method(ModeOfPayment.valueOf(paymentRequest.method()))
                        .build();
                paymentRepository.save(payment);
                saved.getPayments().add(payment);
            }
        }
        updateCustomerPendingAmount(customer, saved, paidAmount);
        return createSaleResponseFromSale(saved);
    }

    private SaleResponse createSaleResponseFromSale(Sale sale) {
        String customerName = sale.getCustomer().getName();
        Double pendingAmount = sale.getCustomer().getPendingAmount();

        List<ProductBuyList> productBuyLists = sale.getItems().stream().map(item ->
                new ProductBuyList(
                        item.getProduct().getName(),
                        item.getQuantitySold(),
                        item.getPricePerUnit(),
                        item.getTotalQuantityPrice()
                )).toList();

        double amountPaid = sale.getPayments().stream()
                .mapToDouble(payment -> payment.getAmount() != null ? payment.getAmount() : 0.0)
                .sum();

        return new SaleResponse(
                sale.getSaleId(),
                sale.getDate(),
                customerName,
                productBuyLists,
                sale.getSubTotal(),
                sale.getDiscountAmount(),
                sale.getTaxAmount(),
                sale.getTotalAmount(),
                amountPaid,
                pendingAmount,
                "REC" + sale.getSaleId() // Example receipt key
        );
    }

    private void updateCustomerPendingAmount(Customer customer, Sale saved, Double paidAmount) {

        Double pendingAmount = customer.getPendingAmount() != null ? customer.getPendingAmount() : 0.0;
        Double totalAmount = saved.getTotalAmount();
        customer.setPendingAmount(pendingAmount + totalAmount - paidAmount);
    }

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found with ID: " + saleId));

        return mapToDTO(sale);
    }

    private SaleResponseDTO mapToDTO(Sale sale) {
        List<SaleItemDTO> items = sale.getItems().stream()
                .map(item -> new SaleItemDTO(
                        item.getProduct().getName(),
                        item.getQuantitySold(),
                        item.getPricePerUnit(),
                        item.getTotalQuantityPrice()
                ))
                .toList();

        return new SaleResponseDTO(
                sale.getSaleId(),
                sale.getDate().toString(),
                sale.getSubTotal(),
                sale.getDiscountAmount(),
                sale.getTaxAmount(),
                sale.getTotalAmount(),
                items
        );
    }


//    @Override
//    public Sale getSaleById(Long saleId) {
//        return saleRepository.findById(saleId).orElseThrow(() ->
//                new RuntimeException("Sale not found with ID: " + saleId));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Sale> getAllSales() {
//        return saleRepository.findAll();
//    }

//    @Override
//    public Sale updateSale(Long saleId, SaleRequest saleRequest) {
//        Sale sale = getSaleById(saleId);
//        sale.setFarmerName(saleRequest.farmerName());
//        sale.setFarmerContact(saleRequest.farmerContact());
//        sale.setUserId(saleRequest.userId());
//        sale.setDate(saleRequest.date());
//        sale.setTotalAmount(saleRequest.totalAmount());
//        return saleRepository.save(sale);
//}

    @Override
    @Transactional
    public List<Sale> findByCustomerCustomerId(Long customerId) {
        return saleRepository.findByCustomerCustomerId(customerId);
    }
}
