package com.example.productlisting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class ProductListingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductListingApplication.class, args);
    }
}
