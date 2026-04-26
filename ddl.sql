-- 상품 테이블
CREATE TABLE products (
  id              BIGSERIAL       PRIMARY KEY,
  name            VARCHAR(255)    NOT NULL,
  quantity        BIGINT          NOT NULL DEFAULT 0,
  status          VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',
  created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
  updated_at      TIMESTAMP       NOT NULL DEFAULT NOW(),

  CONSTRAINT chk_products_quantity_non_negative CHECK (quantity >= 0),
  CONSTRAINT chk_products_status CHECK (status IN ('ACTIVE', 'DISCONTINUED', 'SUSPENDED'))
);

CREATE INDEX idx_products_name ON products (name);

-- 입출고 이력 테이블
CREATE TABLE stock_transactions (
    id              BIGSERIAL       PRIMARY KEY,
    product_id      BIGINT          NOT NULL,
    type            VARCHAR(20)     NOT NULL,
    quantity        BIGINT          NOT NULL,
    memo            VARCHAR(500),
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_stock_tx_product FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT chk_stock_tx_quantity_positive CHECK (quantity > 0),
    CONSTRAINT chk_stock_tx_type CHECK (type IN ('INBOUND', 'OUTBOUND'))
);

CREATE INDEX idx_stock_tx_product_id ON stock_transactions (product_id);
CREATE INDEX idx_stock_tx_product_type ON stock_transactions (product_id, type, created_at DESC);