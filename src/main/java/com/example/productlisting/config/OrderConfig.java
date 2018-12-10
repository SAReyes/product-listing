package com.example.productlisting.config;

import com.example.productlisting.mapper.OrderMapper;
import com.example.productlisting.repository.OrderRepository;
import com.example.productlisting.service.OrderService;
import com.example.productlisting.service.OrderServiceImpl;
import com.example.productlisting.utils.DateUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }

    @Bean
    public OrderService orderService(OrderRepository repository, DateUtils dateUtils) {
        return new OrderServiceImpl(repository, dateUtils);
    }
}
