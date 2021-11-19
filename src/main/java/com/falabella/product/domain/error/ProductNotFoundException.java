package com.falabella.product.domain.error;

/**
 * @author german
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
