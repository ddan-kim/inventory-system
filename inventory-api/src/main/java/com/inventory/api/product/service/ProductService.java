package com.inventory.api.product.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.api.product.dto.response.ProductResponse;
import com.inventory.api.product.repository.ProductRepository;
import com.inventory.core.exception.ErrorCode;
import com.inventory.core.exception.InventorySystemException;
import com.inventory.domain.product.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public ProductResponse getStock(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new InventorySystemException(ErrorCode.PRODUCT_NOT_FOUND, "상품을 찾을 수 없습니다. ID: " + productId));
		return ProductResponse.from(product);
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable).map(ProductResponse::from);
	}
}
