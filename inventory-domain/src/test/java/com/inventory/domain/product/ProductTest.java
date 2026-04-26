package com.inventory.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.inventory.core.exception.InventorySystemException;
import com.inventory.domain.product.entity.Product;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Product 엔티티 테스트")
class ProductTest {

	@Nested
	@DisplayName("상품 생성")
	class Create {

		@Test
		@DisplayName("유효한 값으로 상품을 생성한다")
		void success() {
			Product product = Product.create("상품 A", 100L);

			assertThat(product.getName()).isEqualTo("상품 A");
			assertThat(product.getQuantity()).isEqualTo(100L);
		}

		@Test
		@DisplayName("상품명이 빈 문자열이면 예외를 발생시킨다")
		void failWithBlankName() {
			assertThatThrownBy(() -> Product.create("", 100L))
				.isInstanceOf(InventorySystemException.class)
				.hasMessageContaining("상품명은 필수");
		}

		@Test
		@DisplayName("수량이 음수이면 예외를 발생시킨다")
		void failWithNegativeQuantity() {
			assertThatThrownBy(() -> Product.create("상품 A", -1L))
				.isInstanceOf(InventorySystemException.class)
				.hasMessageContaining("유효하지 않은 수량");
		}
	}

	@Nested
	@DisplayName("입고 (increaseStock)")
	class IncreaseStock {

		@Test
		@DisplayName("재고를 증가시킨다")
		void success() {
			Product product = Product.create("상품 A", 100L);
			product.increaseStock(50L);
			assertThat(product.getQuantity()).isEqualTo(150L);
		}

		@Test
		@DisplayName("입고 수량이 0 이하이면 예외를 발생시킨다")
		void failWithZero() {
			Product product = Product.create("상품 A", 100L);
			assertThatThrownBy(() -> product.increaseStock(0L))
				.isInstanceOf(InventorySystemException.class);
		}
	}

	@Nested
	@DisplayName("출고 (decreaseStock)")
	class DecreaseStock {

		@Test
		@DisplayName("재고를 감소시킨다")
		void success() {
			Product product = Product.create("상품 A", 100L);
			product.decreaseStock(30L);
			assertThat(product.getQuantity()).isEqualTo(70L);
		}

		@Test
		@DisplayName("재고를 전부 출고할 수 있다")
		void decreaseToZero() {
			Product product = Product.create("상품 A", 50L);
			product.decreaseStock(50L);
			assertThat(product.getQuantity()).isEqualTo(0L);
		}

		@Test
		@DisplayName("재고보다 많은 수량을 출고하면 예외를 발생시킨다")
		void failWithInvalidStock() {
			Product product = Product.create("상품 A", 30L);
			assertThatThrownBy(() -> product.decreaseStock(50L))
				.isInstanceOf(InventorySystemException.class)
				.hasMessageContaining("재고가 부족");
		}
	}
}
