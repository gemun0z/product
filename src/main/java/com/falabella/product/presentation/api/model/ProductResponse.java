package com.falabella.product.presentation.api.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * @author german
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ProductResponse extends RepresentationModel<ProductResponse> implements Serializable {
    private final DataResponse data;
}
