package com.roche.service;

import com.roche.domain.Product;
import com.roche.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        if(repository.findById(product.getSku()).isPresent()){
            throw new DuplicateKeyException("Product with a given id already exists");
        }
        repository.save(product);
    }

    public void update(Product product) {
        repository.save(product);
    }

    public void delete(Product product) {
        product.setDeleted(true);
        repository.save(product);
    }

    public void deleteById(String productId) {
        Product product = get(productId);
        product.setDeleted(true);
        repository.save(product);
    }

    public Product get(String productId) {
        return repository.findBySkuAndDeletedFalse(productId);
    }

    public List<Product> getAll() {
        return repository.findByDeletedFalse();
    }
}
