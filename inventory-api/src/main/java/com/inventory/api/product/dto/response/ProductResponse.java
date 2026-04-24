package com.inventory.api.product.dto.response;

import com.inventory.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String code;
    private String name;
    private Long quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
