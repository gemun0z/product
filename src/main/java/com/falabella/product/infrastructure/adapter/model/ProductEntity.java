package com.falabella.product.infrastructure.adapter.model;

import com.falabella.product.domain.model.Product;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author german
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @Column(name = "SKU", length = 12, nullable = false)
    private String sku;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "BRAND", length = 50, nullable = false)
    private String brand;

    @Column(name = "SIZE")
    private String size;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "PRINCIPAL_IMAGE", nullable = false)
    private String principalImage;

    @ElementCollection
    @Column(name = "OTHER_IMAGES")
    private List<String> otherImages;

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
