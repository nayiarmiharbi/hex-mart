package com.hex_mart.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hex_mart.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional query methods can be defined here if needed
    
}
