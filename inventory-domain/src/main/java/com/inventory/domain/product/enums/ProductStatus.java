package com.inventory.domain.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

	ACTIVE("운영중"),
	DISCONTINUED("단종"),
	SUSPENDED("일시중단");

	private final String description;
}

