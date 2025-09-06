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
@AllArgsConstructor
@NoArgsConstructor
public class DetailItemDto {

    private AuctionStatus status;

    // 판매자 이름
    private String sellerName;

    // 물품 설명
    private String description;

    // 카테고리
    private Category category;

    // 물품 수량
    private Integer quantity;

    // 물품 단위
    private UnitOfMeasure unitOfMeasure;

    // 시작 가격
    private BigDecimal startPrice;

    // 즉시 구매 가격
    private BigDecimal buyNowPrice;

    // 최소 입찰 금액
    private BigDecimal minBidIncrement;

    // 경매 입찰 횟수
    private Integer bidCount;

    // 현재 최고 가격
    private BigDecimal currentBidPrice;

    // 마지막 입찰 시간
    private LocalDateTime lastBidAt;

    // 경매 시작 시간
    private LocalDateTime startAt;

    // 경매 종료 시간
    private LocalDateTime endAt;
}
