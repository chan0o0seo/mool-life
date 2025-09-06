package com.example.backend.auction.service;

import com.example.backend.auction.model.Item;
import com.example.backend.auction.model.dto.DetailItemDto;
import com.example.backend.auction.model.dto.LightItemDto;
import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    /**
     * 경매 물품의 상세 정보 조회
     *
     * @param itemId 조회할 경매 물품의 id
     * @return 사용자에게 보여줄 정보를 담은 dto
     */
    @Transactional(readOnly = true)
    public DetailItemDto getDetailById(UUID itemId) {
        Item item = itemService.getItem(itemId);

        return Item.toDetailItemDto(item);
    }

    public List<LightItemDto> getItemsByStatus(AuctionStatus status) {
        return itemRepository.findByStatus(status).stream().map(Item::toLightItemDto).toList();
    }

    public List<LightItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(Item::toLightItemDto).toList();
    }

}
