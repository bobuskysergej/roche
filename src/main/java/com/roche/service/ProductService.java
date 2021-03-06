package com.roche.service;

import com.roche.domain.Product;
import com.roche.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void create(Product product) {
        if (repository.findBySkuAndDeletedFalse(product.getSku()) != null) {
            throw new DuplicateKeyException("Product with a given id already exists");
        }
        repository.save(product);
    }

    public Product update(Product product) {
        if (repository.findBySkuAndDeletedFalse(product.getSku()) == null) {
            throw new EntityNotFoundException("Product with a given id does not exist");
        }
        return repository.save(product);
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
        Product product = repository.findBySkuAndDeletedFalse(productId);
        if (product != null) {
            return product;
        }
        throw new EntityNotFoundException("Product with a given id does not exist");
    }

    public List<Product> getAll() {
        return repository.findByDeletedFalse();
    }
}
