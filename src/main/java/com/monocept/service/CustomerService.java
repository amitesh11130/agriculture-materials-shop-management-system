package com.monocept.service;

import com.monocept.entity.Customer;
import com.monocept.response.CustomerResponse;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    CustomerResponse getCustomerById(Long customerId);

    List<CustomerResponse> getAllCustomers();

    Customer updateCustomer(Long customerId, Map<String, Object> update);

}
