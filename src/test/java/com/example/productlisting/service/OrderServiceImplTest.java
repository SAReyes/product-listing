package com.example.productlisting.service;

import com.example.productlisting.domain.Order;
import com.example.productlisting.repository.OrderRepository;
import com.example.productlisting.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private OrderService sut;

    @Mock
    private OrderRepository repository;

    @Mock
    private DateUtils dateUtils;

    private Order foo;
    private Order bar;
    private Order baz;
    private Date now;

    @Before
    public void setUp() {
        now = new Date();

        foo = new Order();
        foo.setId(1L);
        foo.setEmail("foo");
        foo.setCreatedAt(addMinutes(now, -2));
        foo.setProducts(Collections.emptyList());

        bar = new Order();
        bar.setId(2L);
        bar.setEmail("bar");
        bar.setCreatedAt(now);
        bar.setProducts(Collections.emptyList());

        baz = new Order();
        baz.setId(1L);
        baz.setEmail("baz");
        baz.setCreatedAt(addMinutes(now, 2));
        baz.setProducts(Collections.emptyList());

        sut = new OrderServiceImpl(repository, dateUtils);
    }

    @Test
    public void saves_an_order() {
        given(dateUtils.now()).willReturn(now);
        given(repository.save(foo)).willReturn(foo);

        var result = sut.save(foo);

        StepVerifier.create(result)
                .thenConsumeWhile(matches -> {
                    assertThat(matches.getCreatedAt()).isEqualTo(now);
                    verify(repository).save(foo);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void finds_all_orders() {
        given(repository.findAll()).willReturn(Arrays.asList(foo, bar, baz));

        var result = sut.find(null, null);

        StepVerifier.create(result)
                .expectNext(foo, bar, baz)
                .verifyComplete();
    }

    @Test
    public void finds_orders_from_a_given_date() {
        given(repository.findAllByCreatedAtGreaterThan(now)).willReturn(Arrays.asList(foo, bar, baz));

        var result = sut.find(now, null);

        StepVerifier.create(result)
                .expectNext(foo, bar, baz)
                .verifyComplete();
    }

    @Test
    public void finds_orders_up_to_a_given_date() {
        given(repository.findAllByCreatedAtLessThan(now)).willReturn(Arrays.asList(foo, bar, baz));

        var result = sut.find(null, now);

        StepVerifier.create(result)
                .expectNext(foo, bar, baz)
                .verifyComplete();
    }

    @Test
    public void finds_orders_in_between_two_dates() {
        given(repository.findAllByCreatedAtBetween(now, now)).willReturn(Arrays.asList(foo, bar, baz));

        var result = sut.find(now, now);

        StepVerifier.create(result)
                .expectNext(foo, bar, baz)
                .verifyComplete();
    }

    private Date addMinutes(Date date, int minutes) {
        return new Date(date.getTime() + (minutes * 1000 * 60));
    }
}
