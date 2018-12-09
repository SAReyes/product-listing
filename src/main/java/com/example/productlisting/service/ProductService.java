package com.example.productlisting.service;

import com.example.productlisting.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();

    Mono<Product> save(Product product);

    Mono<Product> update(Long id, Product product);

    Mono<Product> patch(Long id, Product product);
}
