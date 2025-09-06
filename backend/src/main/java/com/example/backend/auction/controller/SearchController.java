package com.example.backend.auction.controller;

import com.example.backend.exception.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class SearchController {

    @GetMapping
    public ResponseEntity<BaseResponse<Boolean>> getAllItems(

    ) {
        // todo: 전체 경매 리스트 (상태별 필터링: LIVE, UPCOMING, ENDED)
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> getDetailItem(

    ) {
        // todo: 특정 경매 상세
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
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
