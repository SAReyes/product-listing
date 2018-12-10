package com.example.productlisting.config;

import com.example.productlisting.utils.DateUtils;
import com.example.productlisting.utils.NullAwareBeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public NullAwareBeanUtils nullAwareBeanUtils() {
        return new NullAwareBeanUtils();
    }

    @Bean
    public DateUtils dateUtilsBean() {
        return new DateUtils();
    }
}
