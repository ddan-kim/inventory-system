package com.inventory.api.product.dto.response;

import com.inventory.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private Long quantity;
    private String statusDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getQuantity(),
            product.getStatus().getDescription(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
