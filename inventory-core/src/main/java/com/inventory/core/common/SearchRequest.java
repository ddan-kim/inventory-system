package com.inventory.core.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SearchRequest {

	@Schema(description = "페이지 번호", defaultValue = "0")
	private int page = 0;

	@Schema(description = "페이지 크기", defaultValue = "20")
	private int size = 20;

	public Pageable toPageable() {
		return PageRequest.of(page, size);
	}

	public Pageable toPageable(Sort sort) {
		return PageRequest.of(page, size, sort);
	}
}
