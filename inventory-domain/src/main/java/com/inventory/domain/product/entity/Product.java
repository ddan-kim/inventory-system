package com.inventory.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        validateCode(code);
        validateName(name);
        validateQuantity(quantity);
        this.code = code;
        this.name = name;
        this.quantity = quantity != null ? quantity : 0L;
        this.safetyStock = safetyStock != null ? safetyStock : 0L;
        this.status = status != null ? status : ProductStatus.ACTIVE;
        this.categoryId = categoryId;
    }

    public static Product create(String code, String name, Long initialQuantity,
        Long safetyStock, Long categoryId) {
        return Product.builder()
            .code(code)
            .name(name)
            .quantity(initialQuantity)
            .safetyStock(safetyStock)
            .categoryId(categoryId)
            .build();
    }

    /**
     * 입고 처리 - 재고 수량을 증가시킨다.
     */
    public void increaseStock(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("입고 수량은 1 이상이어야 합니다. 입력값: " + amount);
        }
        this.quantity += amount;
    }

    /**
     * 출고 처리 - 재고 수량을 감소시킨다.
     * 재고 수량은 음수가 될 수 없다.
     */
    public void decreaseStock(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("출고 수량은 1 이상이어야 합니다. 입력값: " + amount);
        }
        if (this.quantity < amount) {
            throw new IllegalStateException(String.format("재고가 부족합니다. 현재 재고: %d, 요청 수량: %d", this.quantity, amount));
        }
        this.quantity -= amount;
    }

    public boolean isSafetyStock() {
        return this.quantity <= this.safetyStock;
    }

    public boolean hasEnoughStock(Long amount) {
        return this.quantity >= amount;
    }


    private void validateCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("상품 코드는 필수입니다.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }
    }

    private void validateQuantity(Long quantity) {
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다. 입력값: " + quantity);
        }
    }
}
