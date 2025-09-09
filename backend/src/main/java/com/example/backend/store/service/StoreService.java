package com.example.backend.store.service;

import com.example.backend.auth.user.model.Users;
import com.example.backend.auth.user.service.UserService;
import com.example.backend.store.model.Store;
import com.example.backend.store.model.dto.LightStoreDto;
import com.example.backend.store.model.dto.StoreRegDto;
import com.example.backend.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final UserService userService;
    private final StoreRepository storeRepository;

    /**
     * 새로운 매장 등록
     *
     * @param storeRegDto 매장 정보가 담긴 dto
     * @param email       등록하는 사용자의 email
     */
    @Transactional
    public void registerStore(StoreRegDto storeRegDto, String email) {
        Users user = userService.findUserByEmail(email);

        storeRepository.save(Store.toEntity(storeRegDto, user));
    }

    /**
     * 매장 정보 수정
     *
     * @param storeId     수정할 매장의 아이디
     * @param storeRegDto 수정할 매장의 정보
     */
    @Transactional
    public void updateStore(UUID storeId, StoreRegDto storeRegDto) {
        Store store = getStoreById(storeId);

        store.updateFromDto(storeRegDto);

        storeRepository.save(store);
    }

    /**
     * email로 매장 조회
     *
     * @param email 조회할 사용자의 email
     * @return 사용자가 등록한 매장 목록
     */
    @Transactional(readOnly = true)
    public List<LightStoreDto> getStoreByEmail(String email) {
        return storeRepository.findByOwnerEmail(email)
                .stream().map(Store::toDto).toList();
    }

    /**
     * 매장 삭제
     *
     * @param storeId 삭제할 매장의 아이디
     */
    @Transactional
    public void deleteStore(UUID storeId) {
        storeRepository.deleteById(storeId);
    }

    /**
     * 매장 정보 조회
     *
     * @param storeId 조회할 매장의 아이디
     * @return 매장 정보 dto
     */
    public LightStoreDto getStore(UUID storeId) {
        return Store.toDto(getStoreById(storeId));
    }

    /**
     * 모든 매장 조회
     *
     * @return 모든 매장 목록
     */
    @Transactional(readOnly = true)
    public List<LightStoreDto> getStores() {
        return storeRepository.findAll().stream().map(Store::toDto).toList();
    }

    /**
     * 아이디로 매장 조회
     *
     * @param storeId 조회할 매장 아이디
     * @return 매장엔티티
     */
    public Store getStoreById(UUID storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }
}
