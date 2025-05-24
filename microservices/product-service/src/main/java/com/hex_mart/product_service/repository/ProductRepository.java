package com.hex_mart.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hex_mart.product_service.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    
}
