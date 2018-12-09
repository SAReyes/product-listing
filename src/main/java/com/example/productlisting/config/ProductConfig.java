package com.example.productlisting.config;

import com.example.productlisting.mapper.ProductMapper;
import com.example.productlisting.repository.ProductRepository;
import com.example.productlisting.service.ProductService;
import com.example.productlisting.service.ProductServiceImpl;
import com.example.productlisting.utils.DateUtils;
import com.example.productlisting.utils.NullAwareBeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public NullAwareBeanUtils nullAwareBeanUtils() {
        return new NullAwareBeanUtils();
    }

    @Bean
    public DateUtils dateUtilsBean() {
        return new DateUtils();
    }

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public ProductService productService(ProductRepository repository) {
        return new ProductServiceImpl(repository, nullAwareBeanUtils(), dateUtilsBean());
    }
}
