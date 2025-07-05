package com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.mapper;

import com.seti.challenge.franchiseservice.domain.model.product.Product;
import com.seti.challenge.franchiseservice.infrastructure.drivenadapter.product.entity.ProductEntity;

public class ProductMapper {
    private ProductMapper() {}

    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .stock(entity.getStock())
                .branchId(entity.getBranchId())
                .build();
    }

    public static ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .stock(domain.getStock())
                .branchId(domain.getBranchId())
                .build();
    }
}