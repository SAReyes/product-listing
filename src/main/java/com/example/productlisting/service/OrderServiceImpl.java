package com.example.productlisting.service;

import com.example.productlisting.domain.Order;
import com.example.productlisting.repository.OrderRepository;
import com.example.productlisting.utils.DateUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.function.Supplier;

public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;
    private DateUtils dateUtils;

    public OrderServiceImpl(OrderRepository repository, DateUtils dateUtils) {
        this.repository = repository;
        this.dateUtils = dateUtils;
    }

    @Override
    public Flux<Order> find(Date from, Date to) {
        Supplier<Iterable<Order>> orders;

        if (from != null && to != null) {
            orders = () -> repository.findAllByCreatedAtBetween(from, to);
        } else if (from == null && to != null) {
            orders = () -> repository.findAllByCreatedAtLessThan(to);
        } else if (from != null) {
            orders = () -> repository.findAllByCreatedAtGreaterThan(from);
        } else {
            orders = () -> repository.findAll();
        }

        return Mono.fromCallable(orders::get)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Order> save(Order order) {
        return Mono.just(order)
                .log()
                .zipWith(Mono.just(dateUtils.now()), (o, now) -> {
                    o.setCreatedAt(now);
                    o.setModifiedAt(now);
                    return o;
                })
                .map(it -> repository.save(it));
    }
}
