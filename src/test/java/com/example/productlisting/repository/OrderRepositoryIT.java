package com.example.productlisting.repository;

import com.example.productlisting.domain.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryIT {

    @Autowired
    private OrderRepository sut;

    private SimpleDateFormat format;

    private Order order1;
    private Order order2;
    private Order order3;

    @Before
    public void setUp() throws ParseException {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        order1 = new Order();
        order1.setEmail("a@1.a");
        order1.setCreatedAt(format.parse("2017-12-15 10:00"));
        order2 = new Order();
        order2.setEmail("a@2.a");
        order2.setCreatedAt(format.parse("2017-12-15 10:02"));
        order3 = new Order();
        order3.setEmail("a@3.a");
        order3.setCreatedAt(format.parse("2017-12-15 10:04"));

        sut.save(order1);
        sut.save(order2);
        sut.save(order3);
    }

    @Test
    public void fetches_in_between_dates() throws ParseException {
        var from = format.parse("2017-12-15 10:01");
        var to = format.parse("2017-12-15 10:03");

        var result = sut.findAllByCreatedAtBetween(from, to);

        assertThat(result).containsOnly(order2);
    }

    @Test
    public void fetches_dates_greater_than_the_param() throws ParseException {
        var from = format.parse("2017-12-15 10:01");

        var result = sut.findAllByCreatedAtGreaterThan(from);

        assertThat(result).containsExactlyInAnyOrder(order2, order3);
    }


    @Test
    public void fetches_dates_less_than_the_param() throws ParseException {
        var to = format.parse("2017-12-15 10:03");

        var result = sut.findAllByCreatedAtLessThan(to);

        assertThat(result).containsExactlyInAnyOrder(order1, order2);
    }
}
