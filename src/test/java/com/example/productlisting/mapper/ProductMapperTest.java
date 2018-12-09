package com.example.productlisting.mapper;

import com.example.productlisting.domain.Product;
import com.example.productlisting.dto.ProductDto;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductMapperTest {

    private ProductMapper sut;

    @Before
    public void setUp() {
        sut = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    public void maps_correctly_a_domain_object_to_dto() {
        var foo = new Product();
        foo.setId(1L);
        foo.setName("foo");
        foo.setPrice(0.5);

        var result = sut.domainToDto(foo);

        assertThat(result).isInstanceOf(ProductDto.class);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("foo");
        assertThat(result.getPrice()).isEqualTo(0.5);
    }

    @Test
    public void maps_correctly_a_dto_to_domain_object() {
        var foo = new ProductDto();
        foo.setId(1L);
        foo.setName("foo");
        foo.setPrice(0.5);

        var result = sut.dtoToDomain(foo);

        assertThat(result).isInstanceOf(Product.class);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("foo");
        assertThat(result.getPrice()).isEqualTo(0.5);
    }
}
