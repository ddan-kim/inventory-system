package com.inventory.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                        .title("재고 관리 시스템 API")
                        .description("상품 입고/출고/재고 조회를 위한 RESTful API")
                        .version("v1.0.0"));
    }
}
