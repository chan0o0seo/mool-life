package com.example.backend.auction.controller;

import com.example.backend.exception.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @PostMapping
    public ResponseEntity<BaseResponse<Boolean>> register(

    ) {
        // todo: 경매 등록
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> update(

    ) {
        // TODO: 경매 수정 (경매 시작 전까지만 허용)
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> delete(

    ) {
        // TODO: 경매 취소 (경매 시작 전까지만 허용)
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BaseResponse<Boolean>> changeStatus(

    ) {
        // TODO: 경매 아이템 상태 변경
        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }
}
