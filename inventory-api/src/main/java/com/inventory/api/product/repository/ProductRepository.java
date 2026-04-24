package com.inventory.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}