package com.falabella.product.infrastructure.service;

import com.falabella.product.domain.error.ProductNotFoundException;
import com.falabella.product.domain.model.Product;
import com.falabella.product.infrastructure.adapter.ProductRepository;
import com.falabella.product.infrastructure.adapter.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldSaveProduct() {

        ProductEntity entity = buildProductEntity();

        when(repository.save(any(ProductEntity.class))).thenReturn(entity);

        Product product = service.saveProduct(buildProduct());

        verify(repository).save(entity);

        assertion(product, entity);

    }

    @Test
    void shouldGetProductBySku() {

        ProductEntity entity = buildProductEntity();

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));

        Product product = service.getProductBySku("FAL-1111111");

        verify(repository).findById("FAL-1111111");

        assertion(product, entity);

    }

    @Test
    void shouldHandleGetProductBySkuNotFound() {

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> service.getProductBySku("FAL-1111110"));

        assertThat(exception).hasMessage("Product not found sku FAL-1111110");

    }

    @Test
    void shouldUpdateProductBySku() {

        ProductEntity entity = buildProductEntity();

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        when(repository.save(any(ProductEntity.class))).thenReturn(entity);

        Product product = service.updateProductBySku("FAL-1111111", buildProduct());

        verify(repository).findById("FAL-1111111");
        verify(repository).save(entity);

        assertion(product, entity);

    }

    @Test
    void shouldGetAllProducts() {

        ProductEntity entity = buildProductEntity();

        when(repository.findAll()).thenReturn(List.of(entity));

        List<Product> productList = service.getAllProducts();

        verify(repository).findAll();

        assertThat(productList).isNotEmpty();
        assertion(productList.get(0), entity);

    }

    @Test
    void shouldHandleGetAllProductsNotFound() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> service.getAllProducts());

        assertThat(exception).hasMessage("No registered products found");

    }

    @Test
    void shouldDeleteProductBySku() {

        ProductEntity entity = buildProductEntity();

        when(repository.findById(anyString())).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        service.deleteProductBySku("FAL-1111111");

        verify(repository).findById("FAL-1111111");
        verify(repository).delete(entity);
    }

    private ProductEntity buildProductEntity() {
        return ProductEntity.builder()
                .sku("FAL-1111111")
                .name("some-name")
                .brand("some-brand")
                .size("M")
                .price(new BigDecimal(1.00))
                .principalImage("http://localhost/image")
                .otherImages(List.of("http://localhost/other-image")).build();
    }

    private Product buildProduct() {
        return Product.builder()
                .sku("FAL-1111111")
                .name("some-name")
                .brand("some-brand")
                .size("M")
                .price(new BigDecimal(1.00))
                .principalImage("http://localhost/image")
                .otherImages(List.of("http://localhost/other-image")).build();
    }

    private void assertion(Product product, ProductEntity entity) {
        assertThat(product).isNotNull();
        assertThat(product.getSku()).isEqualTo(entity.getSku());
        assertThat(product.getName()).isEqualTo(entity.getName());
        assertThat(product.getBrand()).isEqualTo(entity.getBrand());
        assertThat(product.getSize()).isEqualTo(entity.getSize());
        assertThat(product.getPrice()).isEqualTo(entity.getPrice());
        assertThat(product.getPrincipalImage()).isEqualTo(entity.getPrincipalImage());
        assertThat(product.getOtherImages()).isNotEmpty();
        assertThat(product.getOtherImages().get(0)).isEqualTo(entity.getOtherImages().get(0));
    }
}