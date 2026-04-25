package com.inventory.api.product.dto.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.inventory.core.common.SearchRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest extends SearchRequest {
	@Override
	public Pageable toPageable() {
		return toPageable(Sort.by(Sort.Direction.ASC, "id"));
	}
}
