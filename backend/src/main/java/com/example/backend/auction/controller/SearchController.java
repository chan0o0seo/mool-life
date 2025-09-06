package com.example.backend.auction.controller;

import com.example.backend.auction.model.dto.DetailItemDto;
import com.example.backend.auction.model.dto.LightItemDto;
import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.service.SearchService;
import com.example.backend.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<LightItemDto>>> getAllItems(
            @RequestParam(required = false) AuctionStatus status
    ) {
        List<LightItemDto> items;

        if (status != null) {
            items = searchService.getItemsByStatus(status);
        } else {
            items = searchService.getAllItems();
        }
        return ResponseEntity.ok()
                .body(BaseResponse.success(items));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<BaseResponse<DetailItemDto>> getDetailItem(
            @PathVariable UUID itemId
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(
                        searchService.getDetailById(itemId)));
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<Boolean>> getMyItems(

    ) {
        // todo: 판매자가 올린 경매 리스트
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @GetMapping("/participating")
    public ResponseEntity<BaseResponse<Boolean>> getParticipatingItems(

    ) {
        // todo: 참여중인 경매 리스트
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }
}
