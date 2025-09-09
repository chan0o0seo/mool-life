package com.example.backend.store.model;

import com.example.backend.store.model.dto.LightProductDto;
import com.example.backend.store.model.dto.ProductRegDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Schema(description = "상품 고유 식별자(UUID)", example = "7fa85f64-5717-4562-b3fc-2c963f66afa6")
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "상품을 등록한 상점", requiredMode = Schema.RequiredMode.REQUIRED)
    private Store store;

    @Column(nullable = false)
    @Schema(description = "상품명", example = "알비노 비단뱀")
    private String name;

    @Column(nullable = false)
    @Schema(description = "상품 가격", example = "350000")
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "재고 수량", example = "10")
    private Integer stock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "상품 카테고리 (FISH, REPTILE, CRAYFISH 등)", example = "REPTILE")
    private ProductCategory category;

    @Column
    @Schema(description = "상품 설명", example = "건강한 1년생 알비노 비단뱀")
    private String description;

    @Column
    @Schema(description = "상품 이미지 URL", example = "https://mool-life.s3.amazonaws.com/product/123.jpg")
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    @Schema(description = "상품 등록 시간", example = "2025-01-01T00:00:00")
    private LocalDateTime createdAt;

    @Column
    @Schema(description = "상품 수정 시간", example = "2025-06-30T12:34:56")
    private LocalDateTime updatedAt;

    public static Product toEntity(ProductRegDto productRegDto, Store store) {
        return Product.builder()
                .store(store)
                .name(productRegDto.getName())
                .price(productRegDto.getPrice())
                .stock(productRegDto.getStock())
                .category(productRegDto.getCategory())
                .description(productRegDto.getDescription())
                .imageUrl(productRegDto.getImageUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static LightProductDto toLightProductDto(Product product) {
        return LightProductDto.builder()
                .id(product.getId())
                .storeName(product.getStore().getName())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public void updateFromDto(ProductRegDto productRegDto) {
        this.name = productRegDto.getName();
        this.price = productRegDto.getPrice();
        this.stock = productRegDto.getStock();
        this.category = productRegDto.getCategory();
        this.description = productRegDto.getDescription();
        this.imageUrl = productRegDto.getImageUrl();

    }
}
