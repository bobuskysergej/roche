package com.roche.controller;

import com.roche.controller.dto.ProductDto;
import com.roche.controller.mapper.ProductDtoToProductMapper;
import com.roche.domain.Product;
import com.roche.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.roche.TestUtility.mockProduct;
import static com.roche.TestUtility.mockProductDto;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService service;
    @Captor
    private ArgumentCaptor<Product> productCaptor;
    @Captor
    private ArgumentCaptor<ProductDto> productDtoCaptor;

    private ProductDtoToProductMapper mapper;
    private ProductController controller;

    @Before
    public void setUp() {
        mapper = Mappers.getMapper(ProductDtoToProductMapper.class);
        controller = new ProductController(mapper, service);
    }

    @Test
    public void create() {
        ProductDto productDto = mockProductDto();
        controller.create(productDto);
        verify(service).create(productCaptor.capture());
        assertProducts(productDto, productCaptor.getValue());
    }

    @Test
    public void update() {
        ProductDto productDto = mockProductDto();
        controller.update(productDto, productDto.getId());
        verify(service).update(productCaptor.capture());
        assertProducts(productDto, productCaptor.getValue());
    }

    @Test
    public void get() {
        Product product = mockProduct();
        when(service.get(product.getSku())).thenReturn(product);
        ProductDto productDto = controller.get(product.getSku());
        assertProducts(productDto, product);
    }

    @Test
    public void getAll() {
        Product product = mockProduct();
        when(service.getAll()).thenReturn(asList(product));
        List<ProductDto> productDtos = controller.getAll();
        assertThat(productDtos).isNotEmpty();
        assertProducts(productDtos.get(0), product);
    }

    @Test
    public void deleteById() {
        Product product = mockProduct();
        controller.deleteById(product.getSku());
        verify(service).deleteById(product.getSku());
    }

    public static void assertProducts(ProductDto productDto, Product product) {
        assertThat(productDto.getId()).isEqualTo(product.getSku());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDto.getCreated()).isEqualTo(product.getCreated());
    }
}
