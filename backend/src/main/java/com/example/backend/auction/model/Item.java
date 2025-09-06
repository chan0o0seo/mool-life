package com.example.backend.auction.model;

import com.example.backend.auction.model.dto.DetailItemDto;
import com.example.backend.auction.model.dto.LightItemDto;
import com.example.backend.auction.model.dto.RegisterDto;
import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.model.type.Category;
import com.example.backend.auction.model.type.UnitOfMeasure;
import com.example.backend.auth.user.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    // 판매자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Users seller;

    // 경매에 등록한 물품 이름
    private String title;

    // 물품 설명
    private String description;

    // 카테고리
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    // 경매 물품의 수량
    @Column
    private Integer quantity;

    // 경매 물품의 단위 ex) KG, PIECE..
    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private UnitOfMeasure unitOfMeasure;

    // 시작 가격
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal startPrice;

    // 즉시 구매할 수 있는 가격
    @Column(precision = 19, scale = 2)
    private BigDecimal buyNowPrice;

    // 최소 입찰 금액
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal minBidIncrement;

    // 경매 시작 시간
    @Column(nullable = false)
    private LocalDateTime startAt;

    // 경매 종료 시간
    @Column(nullable = false)
    private LocalDateTime endAt;

    // 경매 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AuctionStatus status;

    // 경매 입찰 횟수
    private Integer bidCount;

    // 현재 최고 가격
    private BigDecimal currentBidPrice;

    // 마지막 입찰 시간
    private LocalDateTime lastBidAt;

    // 낙찰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Users winner;

    // 입찰 정보 리스트
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    public static Item toEntity(RegisterDto registerDto, Users seller) {

        return Item.builder()
                .title(registerDto.getTitle())
                .description(registerDto.getDescription())
                .category(registerDto.getCategory())
                .quantity(registerDto.getQuantity())
                .unitOfMeasure(registerDto.getUnitOfMeasure())
                .startPrice(registerDto.getStartPrice())
                .buyNowPrice(registerDto.getBuyNowPrice())
                .minBidIncrement(registerDto.getMinBidIncrement())
                .startAt(registerDto.getStartAt())
                .endAt(registerDto.getEndAt())
                .seller(seller)
                .status(AuctionStatus.SCHEDULED)
                .bidCount(0)
                .build();
    }

    public static DetailItemDto toDetailItemDto(Item item) {
        return DetailItemDto.builder()
                .status(item.getStatus())
                .sellerName(item.getSeller().getName())
                .description(item.getDescription())
                .category(item.getCategory())
                .quantity(item.getQuantity())
                .unitOfMeasure(item.getUnitOfMeasure())
                .startPrice(item.getStartPrice())
                .buyNowPrice(item.getBuyNowPrice())
                .minBidIncrement(item.getMinBidIncrement())
                .bidCount(item.getBidCount())
                .currentBidPrice(item.getCurrentBidPrice())
                .lastBidAt(item.getLastBidAt())
                .startAt(item.getStartAt())
                .endAt(item.getEndAt())
                .build();
    }

    public static LightItemDto toLightItemDto(Item item) {
        return LightItemDto.builder()
                .name(item.getSeller().getName())
                .title(item.getTitle())
                .category(item.getCategory())
                .quantity(item.getQuantity())
                .unitOfMeasure(item.getUnitOfMeasure())
                .startPrice(item.getStartPrice())
                .buyNowPrice(item.getBuyNowPrice())
                .startAt(item.getStartAt())
                .status(item.getStatus())
                .build();
    }

    public void updateFromDto(RegisterDto dto) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.category = dto.getCategory();
        this.quantity = dto.getQuantity();
        this.unitOfMeasure = dto.getUnitOfMeasure();
        this.startPrice = dto.getStartPrice();
        this.buyNowPrice = dto.getBuyNowPrice();
        this.minBidIncrement = dto.getMinBidIncrement();
        this.startAt = dto.getStartAt();
        this.endAt = dto.getEndAt();
    }

    public void updateStatus(AuctionStatus status) {
        this.status = status;
    }

    public void updateBid(BigDecimal price, Bid bid) {
        this.bids.add(bid);
        this.currentBidPrice = price;
        this.lastBidAt = LocalDateTime.now();
        this.bidCount++;
    }

    public void completeTrade(Users winner) {
        this.winner = winner;
        this.bidCount++;
        this.endAt = LocalDateTime.now();
        this.status = AuctionStatus.ENDED;
    }
}
