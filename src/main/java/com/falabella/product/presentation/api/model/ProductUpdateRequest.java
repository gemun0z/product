package com.falabella.product.presentation.api.model;

import com.falabella.product.domain.model.Product;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author german
 */
@ToString
public class ProductUpdateRequest extends ProductModel implements Serializable {

    @Override
    public Product toDomain() {
        return Product.builder()
                .name(this.name)
                .brand(this.brand)
                .size(this.size)
                .price(this.price)
                .principalImage(this.principalImage)
                .otherImages(this.otherImages).build();
    }
}
