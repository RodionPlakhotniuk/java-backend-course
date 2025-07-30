package org.example.customer.dao;

import org.example.customer.model.Customer;

import java.util.List;

public interface CustomerDao {
    void add(Customer customer);

    Customer findById(Long id);

    void update(Customer customer);

    void delete(Long id);

    List<Customer> findAll();
}
