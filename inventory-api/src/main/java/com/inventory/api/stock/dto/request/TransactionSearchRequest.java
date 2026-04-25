package com.inventory.api.stock.dto.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.inventory.core.common.SearchRequest;
import com.inventory.domain.stock.enums.TransactionType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSearchRequest extends SearchRequest {
	@NotNull(message = "상품 ID는 필수입니다.")
	@Schema(description = "상품 ID", example = "1")
	private Long productId;

	@Schema(description = "이력 타입 (INBOUND/OUTBOUND)", example = "INBOUND")
	private TransactionType type;

	@Override
	public Pageable toPageable() {
		return toPageable(Sort.by(Sort.Direction.DESC, "createdAt"));
	}
}
