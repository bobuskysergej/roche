package com.roche.repository;

import com.roche.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    public Product findBySkuAndDeletedFalse(String sku);
}
