package com.example.backend.store.model;

import com.example.backend.auth.user.model.Users;
import com.example.backend.store.model.dto.LightStoreDto;
import com.example.backend.store.model.dto.StoreRegDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    @Schema(description = "상점 고유 식별자(UUID)",
            example = "bfa85f64-5717-4562-b3fc-2c963f66afa6",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "상점명", example = "아쿠아쿠아", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Column(nullable = false)
    @Schema(description = "상점 주소", example = "서울특별시 강남구 테헤란로 123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @Column(nullable = false)
    @Schema(description = "상점 전화번호", example = "02-123-4567", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Column
    @Schema(description = "상점 설명/소개", example = "아쿠아쿠아", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Column(nullable = false, updatable = false)
    @Schema(description = "상점 등록일시", example = "2025-01-01T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Column
    @Schema(description = "상점 폐업(삭제)일시", example = "2025-06-30T15:20:30", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @Schema(description = "상점 소유자(Users)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Users owner;

    public static Store toEntity(StoreRegDto storeRegDto, Users owner) {
        return Store.builder()
                .name(storeRegDto.getName())
                .address(storeRegDto.getAddress())
                .phoneNumber(storeRegDto.getPhoneNumber())
                .description(storeRegDto.getDescription())
                .createdAt(LocalDateTime.now())
                .owner(owner)
                .build();
    }

    public static LightStoreDto toDto(Store store) {
        return LightStoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .description(store.getDescription())
                .build();
    }

    public void updateFromDto(StoreRegDto storeRegDto) {
        this.name = storeRegDto.getName();
        this.address = storeRegDto.getAddress();
        this.phoneNumber = storeRegDto.getPhoneNumber();
        this.description = storeRegDto.getDescription();
    }
}
