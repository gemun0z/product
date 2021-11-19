package com.falabella.product.presentation.api.model;

import com.falabella.product.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author german
 */
@Data
@Builder
@ToString
public class DataResponse implements Serializable {

    private String sku;
    private String name;
    private String brand;
    private String size;
    private BigDecimal price;
    @JsonProperty(value = "principal_image")
    private String principalImage;
    @JsonProperty(value = "other_images")
    private List<String> otherImages;

    public static DataResponse of(Product product) {
        return DataResponse.builder()
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .size(product.getSize())
                .price(product.getPrice())
                .principalImage(product.getPrincipalImage())
                .otherImages(product.getOtherImages()).build();
    }

}
