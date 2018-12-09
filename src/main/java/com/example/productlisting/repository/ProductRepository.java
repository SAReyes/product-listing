package com.example.productlisting.repository;

import com.example.productlisting.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
