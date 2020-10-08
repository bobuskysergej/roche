package com.roche.service;

import com.roche.domain.Product;
import com.roche.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<Product> productCaptor;

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
        when(repository.findBySkuAndDeletedFalse(product.getSku())).thenReturn(product);
        service.update(product);
        verify(repository).save(product);
    }

    @Test
    public void delete() {
        Product product = mockProduct();
        service.delete(product);
        verify(repository).save(product);
    }

    @Test
    public void deleteById() {
        Product product = mockProduct();
        when(repository.findBySkuAndDeletedFalse(product.getSku())).thenReturn(product);
        assertThat(product.getDeleted()).isFalse();
        service.deleteById(product.getSku());
        verify(repository).findBySkuAndDeletedFalse(product.getSku());
        verify(repository).save(productCaptor.capture());
        assertThat(productCaptor.getValue().getDeleted()).isTrue();
    }

    @Test
    public void get() {
        String productId = UUID.randomUUID().toString();
        when(repository.findBySkuAndDeletedFalse(productId)).thenReturn(mockProduct());
        service.get(productId);
        verify(repository).findBySkuAndDeletedFalse(productId);
    }

    @Test
    public void getAll() {
        Product product1 = mockProduct();
        Product product2 = mockProduct();
        when(repository.findByDeletedFalse()).thenReturn(asList(product1, product2));
        List<Product> products = service.getAll();
        assertThat(products).containsExactlyInAnyOrder(product1, product2);
    }

}
