package com.example.backend.store.service;

import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.store.model.Product;
import com.example.backend.store.model.Store;
import com.example.backend.store.model.dto.LightProductDto;
import com.example.backend.store.model.dto.ProductRegDto;
import com.example.backend.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreService storeService;
    private final ProductRepository productRepository;

    /**
     * 매장의 상품을 등록
     *
     * @param productRegDto 상품을 등록하기 위한 dto
     */
    @Transactional
    public void registerProduct(ProductRegDto productRegDto) {
        Store store = storeService.getStoreById(productRegDto.getStoreId());
        productRepository.save(Product.toEntity(productRegDto, store));
    }

    /**
     * 상품 정보 수정
     *
     * @param productRegDto 상품을 수정하기 위한 dto
     * @param productId     수정할 상품의 id
     */
    @Transactional
    public void updateProduct(ProductRegDto productRegDto, UUID productId) {
        Product product = getProductById(productId);

        product.updateFromDto(productRegDto);

        productRepository.save(product);
    }

    /**
     * 매장의 상품 목록 조회
     *
     * @param storeId 조회할 매장의 아이디
     * @return 상품 목록
     */
    @Transactional(readOnly = true)
    public List<LightProductDto> getProducts(UUID storeId) {

        return productRepository.findByStoreId(storeId).stream().map(Product::toLightProductDto).toList();
    }

    /**
     * 특정 상품의 정보를 조회
     *
     * @param productId 조회할 상품의 아이디
     * @return 상품의 상세 정보
     */
    @Transactional(readOnly = true)
    public LightProductDto getProduct(UUID productId) {
        return Product.toLightProductDto(getProductById(productId));
    }

    /**
     * 상품 삭제
     *
     * @param productId 삭제할 상품의 아이디
     */
    @Transactional
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }

    /**
     * 아이디로 상품 조회하는 유틸 메서드
     *
     * @param id 조회할 상품의 아이디
     * @return 상품 객체
     */
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
