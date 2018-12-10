package com.example.productlisting.mapper;

import com.example.productlisting.domain.Order;
import com.example.productlisting.domain.Product;
import com.example.productlisting.dto.OrderDto;
import com.example.productlisting.dto.ProductDto;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderMapperTest {

    private OrderMapper sut;

    @Before
    public void setUp() {
        sut = Mappers.getMapper(OrderMapper.class);
    }

    @Test
    public void maps_correctly_a_domain_object_to_dto() {
        var now = new Date();

        var foo = new Product();
        foo.setId(1L);
        foo.setName("foo");
        foo.setPrice(0.5);

        var bar = new Order();
        bar.setId(1L);
        bar.setEmail("bar");
        bar.setCreatedAt(now);
        bar.setProducts(Collections.singletonList(foo));

        var result = sut.domainToDto(bar);

        assertThat(result).isInstanceOf(OrderDto.class);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("bar");
        assertThat(result.getProducts()).hasSize(1);
        assertThat(result.getCreatedAt()).isEqualTo(now);
    }

    @Test
    public void maps_correctly_a_dto_to_domain_object() {
        var now = new Date();

        var foo = new ProductDto();
        foo.setId(1L);
        foo.setName("foo");
        foo.setPrice(0.5);

        var bar = new OrderDto();
        bar.setId(1L);
        bar.setEmail("bar");
        bar.setCreatedAt(now);
        bar.setProducts(Collections.singletonList(foo));

        var result = sut.dtoToDomain(bar);

        assertThat(result).isInstanceOf(Order.class);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("bar");
        assertThat(result.getProducts()).hasSize(1);
        assertThat(result.getCreatedAt()).isEqualTo(now);
    }
}
