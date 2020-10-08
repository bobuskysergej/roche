package com.roche.controller.mapper;

import com.roche.TestUtility;
import com.roche.controller.dto.ProductDto;
import com.roche.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static com.roche.TestUtility.mockProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDtoToProductMapperTest {

    private ProductDtoToProductMapper mapper;

    @Before
    public void setUp() throws Exception {
        this.mapper = Mappers.getMapper(ProductDtoToProductMapper.class);
    }

    @Test
    public void productMapping() {
        Product product = mockProduct();
        ProductDto productDto = mapper.toProductDto(product);
        assertProducts(productDto, product);
    }

    private void assertProducts(ProductDto productDto, Product product) {
        assertThat(productDto.getId()).isEqualTo(product.getSku());
        assertThat(productDto.getName()).isEqualTo(product.getName());
        assertThat(productDto.getPrice()).isEqualTo(product.getPrice());
        assertThat(productDto.getCreated()).isEqualTo(product.getCreated());
    }
}
