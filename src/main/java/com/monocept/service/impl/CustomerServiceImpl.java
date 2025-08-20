package com.monocept.service.impl;

import com.monocept.entity.Customer;
import com.monocept.entity.Sale;
import com.monocept.exception.InvalidFieldException;
import com.monocept.exception.ResourceNotFoundException;
import com.monocept.repository.CustomerRepository;
import com.monocept.response.CustomerResponse;
import com.monocept.response.ProductBuyList;
import com.monocept.service.CustomerService;
import com.monocept.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final SaleService saleService;

    @Override
    @Transactional
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = customerRepository.findWithSalesByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        return buildCustomerResponse(customer);
    }

    @Override
    @Transactional
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::buildCustomerResponse)
                .toList();
    }

    private CustomerResponse buildCustomerResponse(Customer customer) {
        List<Sale> sales = saleService.findByCustomerCustomerId(customer.getCustomerId());
        List<ProductBuyList> productBuyList = sales.stream()
                .flatMap(sale -> sale.getItems().stream()
                        .map(item -> new ProductBuyList(
                                item.getProduct().getName(),
                                item.getQuantitySold(),
                                item.getPricePerUnit(),
                                item.getTotalQuantityPrice()
                        )))
                .toList();

        double totalPaid = sales.stream()
                .flatMap(sale -> sale.getPayments().stream())
                .mapToDouble(payment -> payment.getAmount() != null ? payment.getAmount() : 0.0)
                .sum();

        return mapToCustomerResponse(customer, productBuyList, totalPaid);
    }

    @Override
    public Customer updateCustomer(Long customerId, Map<String, Object> updates) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> customer.setName((String) value);
                case "contactNumber" -> customer.setContactNumber((String) value);
                case "address" -> customer.setAddress((String) value);
                case "pendingAmount" -> {
                    if (value instanceof Number) {
                        customer.setPendingAmount(((Number) value).doubleValue());
                    }
                }
                default -> throw new InvalidFieldException("Invalid field: " + key);
            }
        });

        return customerRepository.save(customer);
    }


    private CustomerResponse mapToCustomerResponse(Customer customer, List<ProductBuyList> productBuyList, double amountPaid) {
        return new CustomerResponse(
                customer.getName(),
                customer.getContactNumber(),
                customer.getAddress(),
                customer.getPendingAmount(),
                amountPaid,
                productBuyList
        );
    }
}
