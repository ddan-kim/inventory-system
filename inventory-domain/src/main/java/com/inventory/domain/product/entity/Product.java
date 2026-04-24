package com.inventory.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.inventory.domain.common.entity.BaseEntity;
import com.inventory.domain.product.enums.ProductStatus;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long safetyStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @Column(name = "category_id")
    private Long categoryId;

    @Builder
    private Product(String code, String name, Long quantity, Long safetyStock, ProductStatus status, Long categoryId) {
        this.code = code;
        this.name = name;
        this.quantity = quantity != null ? quantity : 0L;
        this.safetyStock = safetyStock != null ? safetyStock : 0L;
        this.status = status != null ? status : ProductStatus.ACTIVE;
        this.categoryId = categoryId;
    }

    public static Product create(String code, String name, Long initialQuantity, Long safetyStock, Long categoryId) {
        return Product.builder()
            .code(code)
            .name(name)
            .quantity(initialQuantity)
            .safetyStock(safetyStock)
            .categoryId(categoryId)
            .build();
    }
}
