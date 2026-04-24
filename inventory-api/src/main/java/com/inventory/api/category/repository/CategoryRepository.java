package com.inventory.api.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}