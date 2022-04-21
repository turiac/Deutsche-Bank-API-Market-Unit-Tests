package com.example.unitTests.repository;

import com.example.unitTests.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findUserIdOrderByDateDesc(Long id);
}
