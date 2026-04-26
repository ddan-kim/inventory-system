package com.inventory.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = {
        "com.inventory.api",
        "com.inventory.core"
})
@EntityScan(basePackages = "com.inventory.domain")
@EnableJpaRepositories(basePackages = "com.inventory.api")
@EnableJpaAuditing
@EnableRetry
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
