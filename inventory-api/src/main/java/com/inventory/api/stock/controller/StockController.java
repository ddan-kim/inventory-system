package com.inventory.api.stock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.api.product.dto.response.ProductResponse;
import com.inventory.api.stock.dto.request.InboundRequest;
import com.inventory.api.stock.dto.request.OutboundRequest;
import com.inventory.api.stock.dto.response.InboundResponse;
import com.inventory.api.stock.dto.response.OutboundResponse;
import com.inventory.api.stock.service.StockService;
import com.inventory.core.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "입출고 관리", description = "상품 입고/출고/이력 조회 API")
@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
	private final StockService stockService;

	@Operation(summary = "입고 처리", description = "상품의 재고 수량을 증가시킵니다. 등록되지 않은 상품일 경우 신규 등록 후 입고 처리합니다.")
	@PostMapping("/inbound")
	public ResponseEntity<ApiResponse<InboundResponse>> inbound(@Valid @RequestBody InboundRequest request) {
		InboundResponse response = stockService.inbound(request);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok("입고 처리가 완료되었습니다.", response));
	}

	@Operation(summary = "출고 처리", description = "상품의 재고 수량을 감소시킵니다. 재고 수량은 음수가 될 수 없습니다.")
	@PostMapping("/outbound")
	public ResponseEntity<ApiResponse<OutboundResponse>> outbound(@Valid @RequestBody OutboundRequest request) {
		OutboundResponse response = stockService.outbound(request);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok("출고 처리가 완료되었습니다.", response));
	}

	@Operation(summary = "상품별 재고 조회", description = "상품의 현재 재고 수량을 확인합니다.")
	@GetMapping("/{productId}/stock")
	public ResponseEntity<ApiResponse<ProductResponse>> getStock(@Parameter(description = "상품 ID") @PathVariable Long productId) {
		ProductResponse response = stockService.getStock(productId);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

}