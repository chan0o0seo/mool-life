package com.example.backend.auction.controller;

import com.example.backend.auction.model.dto.LightBidDto;
import com.example.backend.auction.service.BidService;
import com.example.backend.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class BidController {

    private final BidService bidService;

    @PostMapping("/{itemId}/bids")
    public ResponseEntity<BaseResponse<Boolean>> bids(
            @PathVariable UUID itemId,
            @AuthenticationPrincipal User user,
            @RequestParam BigDecimal bidPrice
    ) {
        bidService.placeBid(itemId, user.getUsername(), bidPrice);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }


    @GetMapping("/{itemId}/bids")
    public ResponseEntity<BaseResponse<List<LightBidDto>>> getBidHistories(
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(bidService.getBidHistories(itemId)));
    }
}
