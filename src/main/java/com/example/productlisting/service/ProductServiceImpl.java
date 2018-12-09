package com.example.productlisting.service;

import com.example.productlisting.domain.Product;
import com.example.productlisting.repository.ProductRepository;
import com.example.productlisting.utils.DateUtils;
import com.example.productlisting.utils.NullAwareBeanUtils;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;
    private NullAwareBeanUtils nullAwareBeanUtils;
    private DateUtils dateUtils;

    public ProductServiceImpl(ProductRepository repository,
                              NullAwareBeanUtils nullAwareBeanUtils,
                              DateUtils dateUtils) {
        this.repository = repository;
        this.nullAwareBeanUtils = nullAwareBeanUtils;
        this.dateUtils = dateUtils;
    }

    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    @Override
    public Mono<Product> save(Product product) {
        return Mono.just(product)
                .zipWith(Mono.just(dateUtils.now()), (p, now) -> {
                    p.setCreatedAt(now);
                    p.setModifiedAt(now);
                    return p;
                })
                .map(it -> repository.save(it));
    }

    @Override
    public Mono<Product> update(Long id, Product product) {
        return Mono.just(product)
                .zipWith(Mono.just(dateUtils.now()), (p, now) -> {
                    p.setId(id);
                    p.setCreatedAt(now);
                    p.setModifiedAt(now);
                    return p;
                })
                .map(it -> repository.save(it));
    }

    @Override
    public Mono<Product> patch(Long id, Product product) {
        var modifiedProduct = Mono.just(product)
                .zipWith(Mono.just(dateUtils.now()), (p, now) -> {
                    p.setModifiedAt(now);
                    return p;
                });

        return Mono.just(id)
                .flatMap(it -> Mono.justOrEmpty(repository.findById(it)))
                .zipWith(modifiedProduct, (p1, p2) -> nullAwareBeanUtils.override(p1, p2))
                .map(it -> repository.save(it));
    }
}
