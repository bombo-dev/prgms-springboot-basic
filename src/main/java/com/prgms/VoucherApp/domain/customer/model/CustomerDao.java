package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerDao {

    List<Customer> findAll();

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    void updateStatus(CustomerUpdateRequest updateReqDto);

    void deleteById(UUID id);

    List<Customer> findByCustomerStatus(CustomerStatus customerStatus);
}
