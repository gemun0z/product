package com.falabella.product.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author german
 */
@Data
@Builder
@ToString
public class Product {

    private String sku;
    private String name;
    private String brand;
    private String size;
    private BigDecimal price;
    private String principalImage;
    private List<String> otherImages;

}
