package com.falabella.product.infrastructure.adapter;

import com.falabella.product.infrastructure.adapter.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author german
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
}
