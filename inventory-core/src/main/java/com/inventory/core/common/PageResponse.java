package com.inventory.core.common;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "페이징 응답")
public class PageResponse<T> {

	@Schema(description = "데이터 목록")
	private final List<T> content;

	@Schema(description = "현재 페이지 번호", example = "0")
	private final int page;

	@Schema(description = "페이지 크기", example = "20")
	private final int size;

	@Schema(description = "전체 데이터 수", example = "100")
	private final long totalElements;

	@Schema(description = "전체 페이지 수", example = "5")
	private final int totalPages;

	public PageResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}
}