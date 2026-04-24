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

    // TODO: 상품 식별을 위한 Product Code 도입 고려
    // @Column(nullable = false, unique = true, length = 50)
    // private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Long quantity;

    // TODO: 최소 재고(safetyStock) 도입 시 재고 부족 알림 정책 확장 가능
    // @Column(nullable = false)
    // private Long safetyStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    // TODO: 카테고리 확장 고려 (현재 MVP 범위에서는 제외)
    // private List<Category> categories;

    @Builder
    private Product(String name, Long quantity, ProductStatus status) {
        validateName(name);
        validateQuantity(quantity);

        this.name = name;
        this.quantity = quantity != null ? quantity : 0L;
        this.status = status != null ? status : ProductStatus.ACTIVE;
    }

    /**
     * 상품 생성 (신규 등록)
     */
    public static Product create(String name, Long initialQuantity) {
        return Product.builder()
            .name(name)
            .quantity(initialQuantity != null ? initialQuantity : 0L)
            .status(ProductStatus.ACTIVE)
            .build();
    }

    /**
     * 입고 처리 - 재고 수량 증가
     */
    public void increaseStock(Long amount) {
        validatePositive(amount);
        this.quantity += amount;
    }

    /**
     * 출고 처리 - 재고 수량 감소
     * 재고는 음수가 될 수 없다.
     */
    public void decreaseStock(Long amount) {
        validatePositive(amount);

        if (this.quantity < amount) {
            throw new IllegalStateException(
                String.format("재고가 부족합니다. 현재 재고: %d, 요청 수량: %d", this.quantity, amount)
            );
        }

        this.quantity -= amount;
    }

    /**
     * 재고 충분 여부 확인
     */
    public boolean hasEnoughStock(Long amount) {
        return amount != null && this.quantity >= amount;
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

    private void validatePositive(Long amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다. 입력값: " + amount);
        }
    }
}