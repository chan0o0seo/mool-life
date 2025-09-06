package com.example.backend.auction.model;

import com.example.backend.auth.user.model.Users;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    @Column
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private UnitOfMeasure unitOfMeasure;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AuctionType auctionType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal startPrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal reservePrice;

    @Column(precision = 19, scale = 2)
    private BigDecimal buyNowPrice;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal minBidIncrement;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private AuctionStatus status;

    private Integer bidCount;
    private LocalDateTime lastBidAt;

    @Column(precision = 19, scale = 2)
    private BigDecimal winningBidAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Users winner;
}
