package com.roche.service;

import com.roche.domain.Product;
import com.roche.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void create(Product product) {
        this.repository.save(product);
    }

    public void update(Product product) {
        this.repository.save(product);
    }

    public void delete(Product product) {
        this.repository.delete(product);
    }

    public void deleteById(String productId) {
        this.repository.deleteById(productId);
    }

    public Product get(String productId) {
        return this.repository.getOne(productId);
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }
}
