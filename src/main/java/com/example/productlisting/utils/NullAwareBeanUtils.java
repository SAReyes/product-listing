package com.example.productlisting.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class NullAwareBeanUtils extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        super.copyProperty(dest, name, value);
    }

    public <T> T override(T dest, T origin) {
        try {
            super.copyProperties(dest, origin);
            return dest;
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn("Error copying properties", e);
            throw new RuntimeException(e);
        }
    }
}
