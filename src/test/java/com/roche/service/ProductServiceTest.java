package com.roche.service;

import com.roche.domain.Product;
import com.roche.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.roche.TestUtility.mockProduct;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    private ProductService service;

    @Before
    public void setUp() throws Exception {
        service = new ProductService(repository);
    }

    @Test
    public void create() {
        Product product = mockProduct();
        service.create(product);
        verify(repository).save(product);
    }

    @Test
    public void update() {
        Product product = mockProduct();
        service.update(product);
        verify(repository).save(product);
    }

    @Test
    public void delete() {
        Product product = mockProduct();
        service.delete(product);
        verify(repository).delete(product);
    }

    @Test
    public void deleteById() {
        String productId = UUID.randomUUID().toString();
        service.deleteById(productId);
        verify(repository).deleteById(productId);
    }

    @Test
    public void get() {
        String productId = UUID.randomUUID().toString();
        service.get(productId);
        verify(repository).getOne(productId);
    }

    @Test
    public void getAll() {
        Product product1 = mockProduct();
        Product product2 = mockProduct();
        when(repository.findAll()).thenReturn(asList(product1, product2));
        List<Product> products = service.getAll();
        assertThat(products).containsExactlyInAnyOrder(product1, product2);
    }

}
