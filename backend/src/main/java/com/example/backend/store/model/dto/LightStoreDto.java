package com.example.backend.store.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LightStoreDto {

    private UUID id;

    // 상점 이름
    private String name;

    // 주소
    private String address;

    // 전화번호
    private String phoneNumber;

    // 설명
    private String description;

}
