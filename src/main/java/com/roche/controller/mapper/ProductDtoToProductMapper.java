package com.roche.controller.mapper;

import com.roche.controller.dto.ProductDto;
import com.roche.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoToProductMapper {

    @Mapping(target="sku", source="productDto.id")
    Product toProduct(ProductDto productDto);

    @Mapping(target="id", source="product.sku")
    ProductDto toProductDto(Product product);

}
