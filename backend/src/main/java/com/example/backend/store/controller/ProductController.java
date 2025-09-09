package com.example.backend.store.controller;


import com.example.backend.exception.BaseResponse;
import com.example.backend.store.model.dto.LightProductDto;
import com.example.backend.store.model.dto.ProductRegDto;
import com.example.backend.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<Boolean>> registerProduct(
            @AuthenticationPrincipal User user,
            @RequestBody ProductRegDto productRegDto
    ) {
        productService.registerProduct(productRegDto);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @PutMapping
    public ResponseEntity<BaseResponse<Boolean>> updateProduct(
            @AuthenticationPrincipal User user,
            @RequestBody ProductRegDto productRegDto,
            @RequestParam UUID productId
    ) {
        productService.updateProduct(productRegDto, productId);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Boolean>> deleteProduct(
            @RequestParam UUID productId
    ) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok()
                .body(BaseResponse.success(true));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<LightProductDto>>> getAllProducts(
            @RequestParam UUID storeId
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(productService.getProducts(storeId)));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse<LightProductDto>> getProduct(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok()
                .body(BaseResponse.success(productService.getProduct(productId)));
    }
}
