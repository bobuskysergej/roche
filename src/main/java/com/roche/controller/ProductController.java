package com.roche.controller;

import com.roche.controller.dto.ProductDto;
import com.roche.controller.mapper.ProductDtoToProductMapper;
import com.roche.domain.Product;
import com.roche.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/products")
@RestController
public class ProductController {

    private ProductDtoToProductMapper productMapper;
    private ProductService service;

    @Autowired
    public ProductController(ProductDtoToProductMapper productMapper, ProductService service) {
        this.productMapper = productMapper;
        this.service = service;
    }

    @ApiOperation(value = "Creates a new product", notes = "This endpoint creates a new product in the database.")
    @PostMapping
    public void create(@RequestBody ProductDto productDto) {
        service.create(productMapper.toProduct(productDto));
    }

    @ApiOperation(value = "Updates an exiting product", notes = "This endpoint updates the properties of a product in the database. Anything can be " +
            "updated except for the product sku.")
    @PostMapping("/{id}")
    public void update(@RequestBody ProductDto productDto, @ApiParam(name = "id", value = "Id of the product to be updated") @PathVariable("id") String productId) {
        Product product = productMapper.toProduct(productDto);
        product.setSku(productId);
        service.update(product);
    }

    @ApiOperation(value = "Retrieves the details of a product", notes = "This endpoint retrieves the details of a product from the database.", response = ProductDto.class)
    @GetMapping("/{id}")
    public ProductDto get(@ApiParam(name = "id", value = "Id of the product to be retrieved") @PathVariable("id") String productId) {
        return productMapper.toProductDto(service.get(productId));
    }

    @ApiOperation(value = "Retrieves the details of all products", notes = "This endpoint retrieves the details of all products from the database.", response = ProductDto.class, responseContainer = "List")
    @GetMapping
    public List<ProductDto> getAll() {
        return service.getAll().stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Deletes a product from the database", notes = "The product is actually not deleted but rather a delete flag is set.")
    @DeleteMapping("/{id}")
    public void deleteById(@ApiParam(name = "id", value = "Id of the product to be deleted") @PathVariable("id") String productId) {
        service.deleteById(productId);
    }
}
