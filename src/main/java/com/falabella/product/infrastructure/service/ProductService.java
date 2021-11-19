package com.falabella.product.infrastructure.service;

import com.falabella.product.domain.error.ProductNotFoundException;
import com.falabella.product.domain.model.Product;
import com.falabella.product.domain.port.ProductOperations;
import com.falabella.product.infrastructure.adapter.ProductRepository;
import com.falabella.product.infrastructure.adapter.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author german
 */
@Service
public class ProductService implements ProductOperations {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product saveProduct(Product product) {
        ProductEntity productEntity = repository.save(buildEntity(product));
        return productEntity.toDomain();
    }

    @Override
    public Product getProductBySku(String sku) {
        return getProductEntityBySku(sku).toDomain();
    }

    @Override
    public Product updateProductBySku(String sku, Product product) {
        product.setSku(sku);
        ProductEntity productEntity = getProductEntityBySku(sku);
        repository.save(buildEntity(product));
        return productEntity.toDomain();
    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntityList = repository.findAll();
        if (productEntityList.isEmpty()) {
            throw new ProductNotFoundException("No registered products found");
        }
        return productEntityList.stream().map(ProductEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteProductBySku(String sku) {
        ProductEntity productEntity = getProductEntityBySku(sku);
        repository.delete(productEntity);
    }

    private ProductEntity getProductEntityBySku(String sku) {
        Optional<ProductEntity> productEntity = repository.findById(sku);
        if (!productEntity.isPresent()) {
            throw new ProductNotFoundException("Product not found sku " + sku);
        }
        return productEntity.get();
    }

    private ProductEntity buildEntity(Product product) {
        return ProductEntity.builder()
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .size(product.getSize())
                .price(product.getPrice())
                .principalImage(product.getPrincipalImage())
                .otherImages(product.getOtherImages())
                .build();
    }
}
