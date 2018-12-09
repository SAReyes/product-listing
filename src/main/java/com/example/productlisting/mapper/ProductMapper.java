package com.example.productlisting.mapper;

import com.example.productlisting.domain.Product;
import com.example.productlisting.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id")
    ProductDto domainToDto(Product product);

    @Mapping(target = "id")
    Product dtoToDomain(ProductDto product);
}
