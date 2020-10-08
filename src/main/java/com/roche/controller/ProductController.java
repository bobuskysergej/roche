package com.roche.controller;

import com.roche.domain.Product;
import com.roche.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/products/")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public void create(@RequestBody Product product) {
        this.service.create(product);
    }

    @PostMapping("{id}")
    public void update(@RequestBody Product product, @PathVariable("id") String productId) {
        this.service.update(product);
    }

    @GetMapping("{id}")
    public Product get(String productId) {
        return this.service.get(productId);
    }

    @GetMapping
    public List<Product> getAll() {
        return this.service.getAll();
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String productId) {
        this.service.deleteById(productId);
    }

}
