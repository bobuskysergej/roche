package com.roche.integration;

import com.roche.controller.dto.ProductDto;
import com.roche.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URL;
import java.util.List;

import static com.roche.TestUtility.mockProductDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private URI productsUri;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.productsUri = new URL("http://localhost:" + port + "/products/").toURI();
    }

    @After
    public void tearDown() throws Exception {
        productRepository.deleteAll();
    }

    @Test
    public void createProduct() {
        ProductDto productDto = mockProductDto();
        ResponseEntity<Void> createResponse = template.postForEntity(productsUri, productDto, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<ProductDto> createdProduct = template.getForEntity(productsUri + productDto.getId(), ProductDto.class);
        assertProducts(productDto, createdProduct.getBody());
    }

    @Test
    public void updateProduct() {
        ProductDto productDto = mockProductDto();
        template.postForEntity(productsUri, productDto, Void.class);
        productDto.setName("updatedName-1");
        productDto.setPrice(10d);
        template.put(productsUri + productDto.getId(), productDto, Void.class);
        ResponseEntity<ProductDto> updatedProduct = template.getForEntity(productsUri + productDto.getId(), ProductDto.class);
        assertProducts(productDto, updatedProduct.getBody());
    }

    @Test
    public void findAll() {
        ProductDto productDto = mockProductDto();
        template.postForEntity(productsUri, productDto, Void.class);
        ResponseEntity<List> allProductsResponse = template.getForEntity(productsUri, List.class);
        List<ProductDto> productDtos = (List<ProductDto>) allProductsResponse.getBody();
        assertThat(productDtos.size()).isEqualTo(1);
    }

    @Test
    public void deleteById() {
        ProductDto productDto = mockProductDto();
        template.postForEntity(productsUri, productDto, Void.class);
        ResponseEntity<List> allProductsResponse = template.getForEntity(productsUri, List.class);
        assertThat(allProductsResponse.getBody().size()).isEqualTo(1);
        template.delete(productsUri + productDto.getId());
        allProductsResponse = template.getForEntity(productsUri, List.class);
        assertThat(allProductsResponse.getBody()).isEmpty();
    }


    private void assertProducts(ProductDto productDto1, ProductDto productDto2) {
        assertThat(productDto1.getId()).isEqualTo(productDto2.getId());
        assertThat(productDto1.getName()).isEqualTo(productDto2.getName());
        assertThat(productDto1.getPrice()).isEqualTo(productDto2.getPrice());
        assertThat(productDto1.getCreated()).isEqualTo(productDto2.getCreated());
    }
}
