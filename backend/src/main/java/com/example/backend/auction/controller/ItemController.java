package com.example.backend.auction.controller;

import com.example.backend.auction.model.dto.RegisterDto;
import com.example.backend.auction.service.ItemService;
import com.example.backend.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<BaseResponse<Boolean>> register(
            @AuthenticationPrincipal User user,
            @RequestBody RegisterDto registerDto
    ) {
        itemService.registerItem(registerDto, user.getUsername());

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> update(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user,
            @RequestBody RegisterDto registerDto
    ) {
        itemService.updateItem(id, registerDto, user.getUsername());

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        itemService.deleteItem(id, user.getUsername());

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
