package com.roche.integration;

import com.roche.controller.dto.ProductDto;
import com.roche.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URL;

import static com.roche.TestUtility.mockProductDto;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private URI productsUri;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.productsUri = new URL("http://localhost:" + port + "/products").toURI();
    }

    @Test
    public void createProduct() {
        ProductDto productDto = mockProductDto();
        ResponseEntity<Void> createResponse = template.postForEntity(productsUri, productDto, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductDto createdProduct = template.getForObject(productsUri + "/" + productDto.getId(), ProductDto.class);
        assertProducts(productDto, createdProduct);
    }

    @Test
    public void updateProduct() {
        ProductDto productDto = mockProductDto();
        ResponseEntity<Void> createResponse = template.postForEntity(productsUri, productDto, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        productDto.setName("updatedName-1");
        productDto.setPrice(10d);
        ResponseEntity<Void> updateResponse = template.postForEntity(productsUri + "/" + productDto.getId(), productDto, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductDto updatedProduct = template.getForObject(productsUri + "/" + productDto.getId(), ProductDto.class);
        assertProducts(productDto, updatedProduct);
    }

    private void assertProducts(ProductDto productDto1, ProductDto productDto2) {
        assertThat(productDto1.getId()).isEqualTo(productDto2.getId());
        assertThat(productDto1.getName()).isEqualTo(productDto2.getName());
        assertThat(productDto1.getPrice()).isEqualTo(productDto2.getPrice());
        assertThat(productDto1.getCreated()).isEqualTo(productDto2.getCreated());
    }
}
