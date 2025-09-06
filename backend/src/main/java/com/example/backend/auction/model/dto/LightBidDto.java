package com.example.backend.auction.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LightBidDto {

    // 입찰자 이름
    private String name;

    // 입찰 가격
    private BigDecimal bidPrice;

    // 입찰 시간
    private LocalDateTime bidTime;
}
