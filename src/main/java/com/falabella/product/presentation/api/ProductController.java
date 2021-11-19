package com.falabella.product.presentation.api;

import com.falabella.product.domain.model.Product;
import com.falabella.product.domain.port.ProductOperations;
import com.falabella.product.presentation.api.model.DataResponse;
import com.falabella.product.presentation.api.model.ProductRequest;
import com.falabella.product.presentation.api.model.ProductResponse;
import com.falabella.product.presentation.api.model.ProductUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author german
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class ProductController {

    private final ProductOperations productOperations;

    public ProductController(ProductOperations productOperations) {
        this.productOperations = productOperations;
    }

    @PostMapping(path = "/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> saveProduct(@Valid @RequestBody ProductRequest request) {
        log.info("request: {}", request);
        Product product = productOperations.saveProduct(request.toDomain());
        log.info("response: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembleResponse(DataResponse.of(product)));
    }

    @GetMapping(path = "/product/{sku}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProductBySku(@Valid @Pattern(regexp = "\\bFAL\\b-\\d*", message = "It must comply with the format FAL-XXXXXXX, where X is a number")
                                                           @PathVariable(value = "sku") String sku) {
        log.info("sku: {}", sku);
        Product product = productOperations.getProductBySku(sku);
        log.info("response: {}", product);
        return ResponseEntity.status(HttpStatus.OK).body(assembleResponse(DataResponse.of(product)));
    }

    @PutMapping(path = "/product/{sku}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> updateProduct(@Valid @Pattern(regexp = "\\bFAL\\b-\\d*", message = "It must comply with the format FAL-XXXXXXX, where X is a number")
                                                         @PathVariable(value = "sku") String sku,
                                                         @Valid @RequestBody ProductUpdateRequest request) {
        log.info("sku: {}", sku);
        log.info("request: {}", request);
        Product product = productOperations.updateProductBySku(sku, request.toDomain());
        log.info("response: {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembleResponse(DataResponse.of(product)));
    }

    @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> productList = productOperations.getAllProducts();
        List<DataResponse> dataResponseList = productList
                .stream()
                .map(product -> DataResponse.of(product))
                .collect(Collectors.toList());
        List<ProductResponse> productResponseList = new ArrayList<>();
        dataResponseList.forEach(dataResponse -> {
            productResponseList.add(assembleResponse(dataResponse));
        });
        return ResponseEntity.status(HttpStatus.OK).body(productResponseList);
    }

    @DeleteMapping(path = "/product/{sku}")
    public ResponseEntity<Void> deleteProductBySku(@Valid @Pattern(regexp = "\\bFAL\\b-\\d*", message = "It must comply with the format FAL-XXXXXXX, where X is a number")
                                                   @PathVariable(value = "sku") String sku) {
        log.info("sku: {}", sku);
        productOperations.deleteProductBySku(sku);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ProductResponse assembleResponse(DataResponse dataResponse) {
        ProductResponse productResponse = new ProductResponse(dataResponse);
        productResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductBySku(dataResponse.getSku())).withSelfRel());
        productResponse.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductBySku(dataResponse.getSku())).withRel("product"));
        return productResponse;
    }

}
