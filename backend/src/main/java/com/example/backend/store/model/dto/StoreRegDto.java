package com.example.backend.store.model.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StoreRegDto {

    private String name;

    private String address;

    private String phoneNumber;

    private String description;
}
