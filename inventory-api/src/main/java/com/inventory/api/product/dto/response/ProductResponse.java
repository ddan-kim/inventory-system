package com.inventory.api.product.dto.response;

import com.inventory.domain.product.entity.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "상품 재고 응답")
public class ProductResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품명", example = "베어링")
    private String name;

    @Schema(description = "현재 재고 수량", example = "100")
    private Long quantity;

    @Schema(description = "상품 상태", example = "운영중")
    private String statusDescription;

    @Schema(description = "등록일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
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
