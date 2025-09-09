package com.example.backend.store.model.dto;

import com.example.backend.store.model.ProductCategory;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ProductRegDto {

    private UUID storeId;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private ProductCategory category;

    private String description;

    private String imageUrl;
}
