package com.example.productlisting.service;

import com.example.productlisting.domain.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface OrderService {

    Flux<Order> find(Date from, Date to);

    Mono<Order> save(Order order);
}
