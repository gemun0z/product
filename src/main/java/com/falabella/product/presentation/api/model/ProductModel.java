package com.falabella.product.presentation.api.model;

import com.falabella.product.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author german
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductModel {

    @NotBlank(message = "You must enter a value")
    @Size(min = 3, max = 50, message = "The value must be between 3 and 50 characters")
    @JsonProperty(required = true)
    protected String name;

    @NotBlank(message = "You must enter a value")
    @Size(min = 3, max = 50, message = "The value must be between 3 and 50 characters")
    @JsonProperty(required = true)
    protected String brand;

    protected String size;

    @NotNull(message = "You must enter a value")
    @JsonProperty(required = true)
    @DecimalMin(value = "1.00", message = "The minimum value is 1.00")
    @DecimalMax(value = "99999999.00", message = "The maximum value is 99999999.00")
    protected BigDecimal price;

    @NotBlank(message = "You must enter a value")
    @JsonProperty(value = "principal_image", required = true)
    @Pattern(regexp = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", message = "It must comply with the URL format")
    protected String principalImage;

    @JsonProperty(value = "other_images")
    protected List<String> otherImages;

    public abstract Product toDomain();

}
