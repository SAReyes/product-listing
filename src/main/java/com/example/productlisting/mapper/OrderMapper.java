package com.example.productlisting.mapper;

import com.example.productlisting.domain.Order;
import com.example.productlisting.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id")
    OrderDto domainToDto(Order order);

    @Mapping(target = "id")
    Order dtoToDomain(OrderDto order);
}
