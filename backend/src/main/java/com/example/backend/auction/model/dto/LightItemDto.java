package com.example.backend.auction.model.dto;

import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.model.type.Category;
import com.example.backend.auction.model.type.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LightItemDto {

    // 판매자 이름
    private String name;

    // 경매에 등록한 물품 이름
    private String title;

    // 카테고리
    private Category category;

    // 경매 물품의 수량
    private Integer quantity;

    // 경매 물품의 단위 ex) KG, PIECE..
    private UnitOfMeasure unitOfMeasure;

    // 시작 가격
    private BigDecimal startPrice;

    // 즉시 구매할 수 있는 가격
    private BigDecimal buyNowPrice;

    // 경매 시작 시간
    private LocalDateTime startAt;

    // 경매 상태
    private AuctionStatus status;
}
