package com.example.productlisting.controller;

import com.example.productlisting.dto.OrderDto;
import com.example.productlisting.mapper.OrderMapper;
import com.example.productlisting.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private OrderService service;
    private OrderMapper mapper;

    @Autowired
    public OrderController(OrderService service, OrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    private Flux<OrderDto> get(@RequestParam(name = "from", required = false)
                               @DateTimeFormat(pattern = DATE_FORMAT) Date fromDate,
                               @RequestParam(name = "to", required = false)
                               @DateTimeFormat(pattern = DATE_FORMAT) Date toDate) {
        return service.find(fromDate, toDate)
                .map(it -> mapper.domainToDto(it));
    }

    @PostMapping
    private Mono<OrderDto> post(@RequestBody OrderDto order) {
        return Mono.just(order)
                .map(it -> mapper.dtoToDomain(it))
                .flatMap(it -> service.save(it))
                .map(it -> mapper.domainToDto(it));
    }
}
