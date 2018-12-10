package com.example.productlisting.repository;

import com.example.productlisting.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Iterable<Order> findAllByCreatedAtBetween(Date from, Date to);
    Iterable<Order> findAllByCreatedAtGreaterThan(Date from);
    Iterable<Order> findAllByCreatedAtLessThan(Date to);
}
