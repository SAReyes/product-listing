package com.example.productlisting.utils;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullAwareBeanUtilsTest {

    private NullAwareBeanUtils sut;

    @Before
    public void setUp() {
        sut = new NullAwareBeanUtils();
    }

    @Test
    public void ignores_null_properties() {
        var foo = new TestDto();
        foo.setName("foo");
        foo.setDescription("a dto for foo");

        var bar = new TestDto();
        bar.setName("bar");

        var result = sut.override(foo, bar);

        assertThat(result.getName()).isEqualTo("bar");
        assertThat(result.getDescription()).isEqualTo("a dto for foo");
    }
}
