package com.falabella.product.domain.port;

import com.falabella.product.domain.model.Product;

import java.util.List;

/**
 * @author german
 */
public interface ProductOperations {

    Product saveProduct(Product product);

    Product getProductBySku(String sku);

    Product updateProductBySku(String sku, Product product);

    List<Product> getAllProducts();

    void deleteProductBySku(String sku);

}
