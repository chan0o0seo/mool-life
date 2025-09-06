package com.example.backend.auction.model.dto;

import com.example.backend.auction.model.type.Category;
import com.example.backend.auction.model.type.UnitOfMeasure;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class RegisterDto {

    private String title;

    private String description;

    private Category category;

    private Integer quantity;

    private UnitOfMeasure unitOfMeasure;

    private BigDecimal startPrice;

    private BigDecimal reservePrice;

    private BigDecimal buyNowPrice;

    private BigDecimal minBidIncrement;

    private LocalDateTime startAt;

    private LocalDateTime endAt;
}
