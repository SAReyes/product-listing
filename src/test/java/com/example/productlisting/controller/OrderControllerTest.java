package com.example.productlisting.controller;

import com.example.productlisting.domain.Order;
import com.example.productlisting.dto.OrderDto;
import com.example.productlisting.mapper.OrderMapper;
import com.example.productlisting.service.OrderService;
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

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = {OrderController.class})
public class OrderControllerTest {

    @Autowired
    private WebTestClient sut;

    @MockBean
    private OrderService service;
    @MockBean
    private OrderMapper mapper;

    private Order foo;
    private Order bar;
    private OrderDto fooDto;
    private OrderDto barDto;

    private Date now;

    @Before
    public void setUp() {
        now = new Date();

        foo = new Order();
        foo.setId(1L);
        foo.setEmail("foo");
        bar = new Order();
        bar.setId(2L);
        bar.setEmail("bar");

        fooDto = new OrderDto();
        fooDto.setId(1L);
        fooDto.setEmail("fooDto");
        barDto = new OrderDto();
        barDto.setId(2L);
        barDto.setEmail("barDto");

        given(mapper.domainToDto(foo)).willReturn(fooDto);
        given(mapper.domainToDto(bar)).willReturn(barDto);
        given(mapper.dtoToDomain(fooDto)).willReturn(foo);
        given(mapper.dtoToDomain(barDto)).willReturn(bar);
    }

    @Test
    public void saves_an_order() {
        given(service.save(foo)).willReturn(Mono.just(foo));

        sut.post().uri("/orders")
                .body(Mono.just(fooDto), OrderDto.class)
                .exchange()
                .expectBody(OrderDto.class)
                .isEqualTo(fooDto);

        verify(service).save(foo);
    }

    @Test
    public void finds_all_orders() {
        given(service.find(null, null)).willReturn(Flux.just(foo, bar));

        sut.get().uri("/orders")
                .exchange()
                .expectBodyList(OrderDto.class)
                .hasSize(2)
                .contains(fooDto, barDto);
    }
}
