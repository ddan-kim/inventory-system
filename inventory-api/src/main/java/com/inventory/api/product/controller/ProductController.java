package com.inventory.api.product.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.api.product.dto.request.ProductSearchRequest;
import com.inventory.api.product.dto.response.ProductResponse;
import com.inventory.api.product.service.ProductService;
import com.inventory.core.common.ApiResponse;
import com.inventory.core.common.PageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품 관리", description = "상품 조회, 상태변경 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "상품별 재고 조회", description = "상품의 현재 재고 수량을 확인합니다.")
	@GetMapping("/{productId}/stock")
	public ResponseEntity<ApiResponse<ProductResponse>> getStock(@PathVariable Long productId) {
		return ResponseEntity.ok(ApiResponse.ok(productService.getStock(productId)));
	}

	@Operation(summary = "전체 상품 재고 목록 조회", description = "등록된 전체 상품의 재고 수량을 조회합니다.")
	@GetMapping
	public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(@ModelAttribute ProductSearchRequest searchRequest) {
		return ResponseEntity.ok(new PageResponse<>(productService.getAllProducts(searchRequest.toPageable())));
	}
}
