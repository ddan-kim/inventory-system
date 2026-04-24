package com.inventory.api.category.dto.response;

import java.time.LocalDateTime;

import com.inventory.domain.category.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponse {
	private Long id;
	private String name;
	private LocalDateTime createdAt;

	public static CategoryResponse from(Category category) {
		return new CategoryResponse(
			category.getId(),
			category.getName(),
			category.getCreatedAt()
		);
	}
}
