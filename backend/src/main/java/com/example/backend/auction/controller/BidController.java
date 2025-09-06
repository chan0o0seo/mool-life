package com.example.backend.auction.controller;

import com.example.backend.exception.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class BidController {

    @PostMapping("/{id}/bids")
    public ResponseEntity<BaseResponse<Boolean>> bids(

    ) {
        // todo: 입찰
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PostMapping("/{id}/buy-now")
    public ResponseEntity<BaseResponse<Boolean>> buy(

    ) {
        // todo: 즉시 구매 처리
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @GetMapping("/{id}/bids")
    public ResponseEntity<BaseResponse<Boolean>> getHistory(

    ) {
        // todo: 해당 아이템의 입찰 히스토리 조회
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }
}
