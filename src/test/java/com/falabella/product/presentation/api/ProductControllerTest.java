package com.falabella.product.presentation.api;

import com.falabella.product.domain.error.ProductNotFoundException;
import com.falabella.product.domain.model.Product;
import com.falabella.product.domain.port.ProductOperations;
import com.falabella.product.presentation.api.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductOperations productOperations;

    @Test
    void shouldSaveProduct() throws Exception {

        when(productOperations.saveProduct(any(Product.class))).thenReturn(buildProduct());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/product")
                        .content(asJsonString(buildRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.sku").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.principal_image").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.other_images").exists());

    }

    @Test
    void shouldProductBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/product")
                        .content(asJsonString(buildBadRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetProductBySku() throws Exception {

        when(productOperations.getProductBySku(anyString())).thenReturn(buildProduct());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/product/FAL-1111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.sku").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.principal_image").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.other_images").exists());

    }

    @Test
    void shouldGetProductBySkuNotFound() throws Exception {

        given(productOperations.getProductBySku(anyString())).willThrow(new ProductNotFoundException("Product not found sku FAL-1111110"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/product/FAL-1111110")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        when(productOperations.updateProductBySku(anyString(), any(Product.class))).thenReturn(buildProduct());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/product/FAL-1111111")
                        .content(asJsonString(buildUpdateRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.sku").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.principal_image").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.other_images").exists());

    }

    @Test
    void shouldGetAllProducts() throws Exception {

        when(productOperations.getAllProducts()).thenReturn(List.of(buildProduct()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.sku").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.brand").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.principal_image").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].data.other_images").exists());

    }

    @Test
    void shouldDeleteProductBySku() throws Exception {

        when(productOperations.getProductBySku(anyString())).thenReturn(buildProduct());
        doNothing().when(productOperations).deleteProductBySku("FAL-1111111");

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/product/FAL-1111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    private ProductRequest buildRequest() {
        ProductRequest request = new ProductRequest();
        request.setSku("FAL-1111111");
        request.setName("some-name");
        request.setBrand("some-brand");
        request.setSize("M");
        request.setPrice(new BigDecimal(1.00));
        request.setPrincipalImage("http://localhost/image");
        request.setOtherImages(List.of("http://localhost/other-image"));
        return request;
    }

    private ProductRequest buildBadRequest() {
        ProductRequest request = new ProductRequest();
        request.setSku("FAL11111110");
        request.setName("some-name");
        request.setBrand("some-brand");
        request.setSize("M");
        request.setPrice(new BigDecimal(1.00));
        request.setPrincipalImage("http://localhost/image");
        request.setOtherImages(List.of("http://localhost/other-image"));
        return request;
    }

    private ProductUpdateRequest buildUpdateRequest() {
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setName("some-name");
        request.setBrand("some-brand");
        request.setSize("M");
        request.setPrice(new BigDecimal(1.00));
        request.setPrincipalImage("http://localhost/image");
        request.setOtherImages(List.of("http://localhost/other-image"));
        return request;
    }

    private ProductResponse buildResponse() {
        DataResponse dataResponse =DataResponse.builder()
                .sku("FAL-1111111")
                .name("some-name")
                .brand("some-brand")
                .size("M")
                .price(new BigDecimal(1.00))
                .principalImage("http://localhost/image")
                .otherImages(List.of("http://localhost/other-image")).build();

        ProductResponse response = new ProductResponse(dataResponse);
        response.add(Link.of("http://localhost:8080/api/v1/product/FAL-1111111").withSelfRel());
        response.add(Link.of("http://localhost:8080/api/v1/product/FAL-1111111").withRel("product"));

        return response;
    }

    private Product buildProduct() {
        return Product.builder()
                .sku("FAL-1111111")
                .name("some-name")
                .brand("some-brand")
                .size("M")
                .price(new BigDecimal(1.00))
                .principalImage("http://localhost/image")
                .otherImages(List.of("http://localhost/other-image")).build();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}