package com.example.backend.store.controller;

import com.example.backend.exception.BaseResponse;
import com.example.backend.store.model.dto.LightStoreDto;
import com.example.backend.store.model.dto.StoreRegDto;
import com.example.backend.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<BaseResponse<Boolean>> registerStore(
            @AuthenticationPrincipal User user,
            @RequestBody StoreRegDto storeRegDto
    ) {
        storeService.registerStore(storeRegDto, user.getUsername());

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Boolean>> updateStore(
            @RequestParam UUID storeId,
            @RequestBody StoreRegDto storeRegDto
    ) {
        storeService.updateStore(storeId, storeRegDto);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteStore(
            @PathVariable UUID storeId
    ) {
        storeService.deleteStore(storeId);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<List<LightStoreDto>>> getMyStore(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(storeService.getStoreByEmail(user.getUsername())));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<LightStoreDto>>> getAllStore(

    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(storeService.getStores()));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<BaseResponse<LightStoreDto>> getStore(
            @PathVariable UUID storeId
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(storeService.getStore(storeId)));
    }

}