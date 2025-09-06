package com.example.backend.auction.model;

import com.example.backend.auction.model.dto.LightBidDto;
import com.example.backend.auth.user.model.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    // 입찰한 물품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 입찰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id")
    private Users bidder;

    // 입찰 가격
    private BigDecimal bidPrice;

    // 입찰 시간
    private LocalDateTime bidTime;

    public static Bid create(BigDecimal bidPrice, Item item, Users bidder) {
        return Bid.builder()
                .item(item)
                .bidder(bidder)
                .bidPrice(bidPrice)
                .bidTime(LocalDateTime.now())
                .build();
    }

    public static LightBidDto toLightBidDto(Bid bid) {
        return LightBidDto.builder()
                .bidPrice(bid.bidPrice)
                .bidTime(bid.bidTime)
                .name(bid.bidder.getName())
                .build();
    }

}
