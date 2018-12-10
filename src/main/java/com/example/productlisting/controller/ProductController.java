package com.example.productlisting.controller;

import com.example.productlisting.dto.ProductDto;
import com.example.productlisting.mapper.ProductMapper;
import com.example.productlisting.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;
    private ProductMapper mapper;

    @Autowired
    public ProductController(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Flux<ProductDto> get() {
        return service.findAll()
                .map(it -> mapper.domainToDto(it));
    }

    @PostMapping
    public Mono<ProductDto> post(@RequestBody ProductDto product) {
        return Mono.just(product)
                .map(it -> mapper.dtoToDomain(it))
                .flatMap(it -> service.save(it))
                .map(it -> mapper.domainToDto(it));
    }

    @PutMapping("/{id}")
    public Mono<ProductDto> put(@PathVariable Long id, @RequestBody ProductDto product) {
        return Mono.just(product)
                .map(it -> mapper.dtoToDomain(it))
                .flatMap(it -> service.update(id, it))
                .map(it -> mapper.domainToDto(it));
    }

    @PatchMapping("/{id}")
    public Mono<ProductDto> patch(@PathVariable Long id, @RequestBody ProductDto product) {
        return Mono.just(product)
                .map(it -> mapper.dtoToDomain(it))
                .flatMap(it -> service.patch(id, it))
                .map(it -> mapper.domainToDto(it));
    }
}
