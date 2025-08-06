package org.example.practiceonspringboot.repository;

import org.example.practiceonspringboot.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
