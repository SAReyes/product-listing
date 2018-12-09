package com.example.productlisting.controller;

import com.example.productlisting.domain.Product;
import com.example.productlisting.dto.ProductDto;
import com.example.productlisting.mapper.ProductMapper;
import com.example.productlisting.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = {ProductController.class})
public class ProductControllerTest {

    @Autowired
    private WebTestClient sut;

    @MockBean
    private ProductService productService;
    @MockBean
    private ProductMapper productMapper;

    private Product foo;
    private Product bar;
    private ProductDto fooDto;
    private ProductDto barDto;

    @Before
    public void setUp() {
        foo = new Product();
        foo.setId(1L);
        foo.setName("foo");
        foo.setPrice(1.0);
        bar = new Product();
        bar.setId(2L);
        bar.setName("bar");
        bar.setPrice(2.0);

        fooDto = new ProductDto();
        fooDto.setId(1L);
        fooDto.setName("foo");
        fooDto.setPrice(1.0);
        barDto = new ProductDto();
        barDto.setId(2L);
        barDto.setName("bar");
        barDto.setPrice(2.0);

        given(productMapper.domainToDto(foo)).willReturn(fooDto);
        given(productMapper.domainToDto(bar)).willReturn(barDto);
        given(productMapper.dtoToDomain(fooDto)).willReturn(foo);
        given(productMapper.dtoToDomain(barDto)).willReturn(bar);
    }

    @Test
    public void gets_all_products() {
        given(productService.findAll()).willReturn(Flux.fromIterable(Arrays.asList(foo, bar)));

        sut.get().uri("/products")
                .exchange()
                .expectBodyList(ProductDto.class)
                .hasSize(2)
                .contains(fooDto, barDto);
    }

    @Test
    public void saves_a_product() {
        given(productService.save(foo)).willReturn(Mono.just(foo));

        sut.post().uri("/products")
                .body(Mono.just(foo), Product.class)
                .exchange()
                .expectBody(ProductDto.class)
                .isEqualTo(fooDto);

        verify(productService).save(foo);
    }

    @Test
    public void updates_a_product() {
        given(productService.update(1L, foo)).willReturn(Mono.just(foo));

        sut.put().uri("/products/1")
                .body(Mono.just(foo), Product.class)
                .exchange()
                .expectBody(ProductDto.class)
                .isEqualTo(fooDto);

        verify(productService).update(1L, foo);
    }

    @Test
    public void patches_a_product() {
        given(productService.patch(1L, foo)).willReturn(Mono.just(foo));

        sut.patch().uri("/products/1")
                .body(Mono.just(foo), Product.class)
                .exchange()
                .expectBody(ProductDto.class)
                .isEqualTo(fooDto);

        verify(productService).patch(1L, foo);
    }
}
