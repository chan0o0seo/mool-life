package com.example.backend.auction.service;

import com.example.backend.auction.model.Item;
import com.example.backend.auction.model.dto.RegisterDto;
import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.repository.ItemRepository;
import com.example.backend.auth.user.model.Users;
import com.example.backend.auth.user.service.UserService;
import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    /**
     * 새로운 경매 물품 등록
     *
     * @param registerDto 경매에 필요한 초기 정보가 담긴 dto
     * @param email       판매자의 이메일
     */
    public void registerItem(RegisterDto registerDto, String email) {

        Users user = userService.findUserByEmail(email);

        itemRepository.save(Item.toEntity(registerDto, user));

    }

    /**
     * 경매 물품 정보 수정 (경매 시작 전에만 가능)
     *
     * @param id          경매 물품의 id
     * @param registerDto 수정할 정보가 담긴 dto
     * @param email       판매자의 이메일
     */
    public void updateItem(UUID id, RegisterDto registerDto, String email) {

        Item item = getItem(id);

        // 권한 체크
        checkAuthorization(item, email);

        // 상태 체크 (경매 시작 전까지만 수정 가능)
        if (item.getStartAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.ITEM_CANNOT_UPDATE);
        }

        // 변경 가능 필드만 수정
        item.updateFromDto(registerDto);

        item.updateFromDto(registerDto);

        itemRepository.save(item);
    }

    /**
     * 경매 취소
     *
     * @param id    취소할 물품의 id
     * @param email 판매자의 이메일
     */
    public void deleteItem(UUID id, String email) {

        Item item = getItem(id);

        // 권한 체크
        checkAuthorization(item, email);

        item.updateStatus(AuctionStatus.CANCELLED);

        itemRepository.save(item);
    }

    /**
     * ID로 Item 조회
     *
     * @param id 조회할 item의 id
     * @return Item 엔티티
     */
    public Item getItem(UUID id) {
        // 비관적 락
        return itemRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
    }

    /**
     * Item에 대한 권한이 있는 유저인지 권한 검증
     *
     * @param item  권한을 검증할 item
     * @param email 요청한 user의 email
     */
    public void checkAuthorization(Item item, String email) {
        if (!item.getSeller().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }
}
