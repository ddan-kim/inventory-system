-- 상품 데이터
INSERT INTO products (name, quantity, status, created_at, updated_at) VALUES
    ('베어링 #6205', 150, 'ACTIVE', NOW(), NOW()),
    ('유압 실린더 HY-100', 30, 'ACTIVE', NOW(), NOW()),
    ('컨베이어 벨트 CB-500', 80, 'ACTIVE', NOW(), NOW()),
    ('모터 오일 5W-30', 200, 'ACTIVE', NOW(), NOW()),
    ('에어 필터 AF-200', 500, 'ACTIVE', NOW(), NOW()),
    ('브레이크 패드 BP-50', 0, 'ACTIVE', NOW(), NOW()),
    ('체인 링크 CL-10', 1000, 'ACTIVE', NOW(), NOW()),
    ('단종 부품 DP-01', 5, 'DISCONTINUED', NOW(), NOW());

-- 입출고 이력 데이터
INSERT INTO stock_transactions (product_id, type, quantity, memo, created_at) VALUES
    (1, 'INBOUND', 200, '최초 입고', NOW()),
    (1, 'OUTBOUND', 30, '정비 출고', NOW()),
    (1, 'OUTBOUND', 20, '긴급 출고', NOW()),
    (2, 'INBOUND', 50, '정기 입고', NOW()),
    (2, 'OUTBOUND', 20, '설비 교체', NOW()),
    (3, 'INBOUND', 100, '대량 입고', NOW()),
    (3, 'OUTBOUND', 20, '라인 교체', NOW()),
    (4, 'INBOUND', 300, '분기 입고', NOW()),
    (4, 'OUTBOUND', 50, '정기 점검', NOW()),
    (4, 'OUTBOUND', 50, '정기 점검', NOW()),
    (5, 'INBOUND', 500, '대량 입고', NOW()),
    (7, 'INBOUND', 1000, '벌크 입고', NOW());