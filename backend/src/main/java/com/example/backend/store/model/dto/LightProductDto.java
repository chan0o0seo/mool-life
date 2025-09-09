package com.example.backend.store.model.dto;

import com.example.backend.store.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LightProductDto {

    private UUID id;

    private String storeName;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private ProductCategory category;

    private String description;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
