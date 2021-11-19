package com.falabella.product.presentation.api.model;

import com.falabella.product.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author german
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class ProductRequest extends ProductModel implements Serializable {

    @NotBlank(message = "You must enter a value")
    @Size(min = 11, max = 12, message = "The value must be between 11 and 12 characters")
    @Pattern(regexp = "\\bFAL\\b-\\d*", message = "It must comply with the format FAL-XXXXXXX, where X is a number")
    @JsonProperty(required = true)
    private String sku;

    @Override
    public Product toDomain() {
        return Product.builder()
                .sku(this.sku)
                .name(this.name)
                .brand(this.brand)
                .size(this.size)
                .price(this.price)
                .principalImage(this.principalImage)
                .otherImages(this.otherImages).build();
    }

}
