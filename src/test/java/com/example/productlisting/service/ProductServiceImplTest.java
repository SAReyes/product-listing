package com.example.productlisting.service;

import com.example.productlisting.domain.Product;
import com.example.productlisting.repository.ProductRepository;
import com.example.productlisting.utils.DateUtils;
import com.example.productlisting.utils.NullAwareBeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    private ProductService sut;

    @Mock
    private ProductRepository repository;

    @Mock
    private NullAwareBeanUtils beanUtils;

    @Mock
    private DateUtils dateUtils;

    private Product foo;
    private Product bar;

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

        sut = new ProductServiceImpl(repository, beanUtils, dateUtils);
    }

    @Test
    public void finds_all_products() {
        given(repository.findAll()).willReturn(Arrays.asList(foo, bar));

        var result = sut.findAll();

        StepVerifier.create(result)
                .expectNext(foo, bar)
                .verifyComplete();
    }

    @Test
    public void saves_a_product() {
        var now = new Date();
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
    public void updates_a_product() {
        given(dateUtils.now()).willReturn(new Date());
        given(repository.save(foo)).willReturn(foo);

        var result = sut.update(2L, foo);

        StepVerifier.create(result)
                .thenConsumeWhile(matches -> {
                    assertThat(matches.getId()).isEqualTo(2L);
                    verify(repository).save(foo);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void patches_a_product() {
        var expected = new Product();
        expected.setId(1L);
        expected.setName("expected");
        expected.setPrice(2.0);

        given(dateUtils.now()).willReturn(new Date());
        given(repository.findById(1L)).willReturn(Optional.of(foo));
        given(repository.save(expected)).willReturn(expected);
        given(beanUtils.override(foo, bar)).willReturn(expected);

        var result = sut.patch(1L, bar);

        StepVerifier.create(result)
                .thenConsumeWhile(matches -> {
                    assertThat(matches).isEqualTo(expected);
                    verify(repository).save(expected);
                    return true;
                })
                .verifyComplete();
    }
}
