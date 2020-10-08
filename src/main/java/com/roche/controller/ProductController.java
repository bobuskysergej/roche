package com.roche.controller;

import com.roche.controller.dto.ProductDto;
import com.roche.controller.mapper.ProductDtoToProductMapper;
import com.roche.domain.Product;
import com.roche.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/products/")
@RestController
public class ProductController {

    private ProductDtoToProductMapper productMapper;
    private ProductService service;

    @Autowired
    public ProductController(ProductDtoToProductMapper productMapper, ProductService service) {
        this.productMapper = productMapper;
        this.service = service;
    }

    @PostMapping
    public void create(@RequestBody ProductDto productDto) {
        service.create(productMapper.toProduct(productDto));
    }

    @PostMapping("{id}")
    public void update(@RequestBody ProductDto productDto, @PathVariable("id") String productId) {
        Product product = productMapper.toProduct(productDto);
        product.setSku(productId);
        service.update(product);
    }

    @GetMapping("{id}")
    public ProductDto get(@PathVariable("id") String productId) {
        return productMapper.toProductDto(service.get(productId));
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return service.getAll().stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") String productId) {
        service.deleteById(productId);
    }
}
