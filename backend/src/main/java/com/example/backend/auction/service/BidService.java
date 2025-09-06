package com.example.backend.auction.service;

import com.example.backend.auction.model.Bid;
import com.example.backend.auction.model.Item;
import com.example.backend.auction.model.dto.LightBidDto;
import com.example.backend.auction.model.type.AuctionStatus;
import com.example.backend.auction.repository.ItemRepository;
import com.example.backend.auth.user.model.Users;
import com.example.backend.auth.user.service.UserService;
import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BidService {

    final private ItemService itemService;
    private final ItemRepository itemRepository;
    private final UserService userService;

    /**
     * 새로운 입찰 등록
     *
     * @param itemId   입찰을 등록할 물품
     * @param email    입찰한 사용자의 이메일
     * @param bidPrice 입찰 가격
     */
    @Transactional
    public void placeBid(UUID itemId, String email, BigDecimal bidPrice) {
        Item item = itemService.getItem(itemId);
        Users user = userService.findUserByEmail(email);

        // 경매 상태 확인
        if (!item.getStatus().equals(AuctionStatus.LIVE)) {
            throw new CustomException(ErrorCode.ITEM_NOT_LIVE);
        }
        // 현재가 가져오기 (없으면 시작가로 대체)
        BigDecimal currentPrice = item.getCurrentBidPrice() != null
                ? item.getCurrentBidPrice()
                : item.getStartPrice();

        // 최소 입찰 단위 확인
        BigDecimal minAllowed = currentPrice.add(item.getMinBidIncrement());
        if (bidPrice.compareTo(minAllowed) < 0) {
            throw new CustomException(ErrorCode.BID_INCREMENT_TOO_LOW);
        }

        Bid bid = Bid.create(bidPrice, item, user);

        // 입찰 기록 저장
        item.updateBid(bidPrice, bid);

        // 즉시 구매 가격보다 높은지 확인
        if (item.getBuyNowPrice() != null
                && item.getCurrentBidPrice().compareTo(item.getBuyNowPrice()) >= 0) {
            item.completeTrade(user);
        }

        itemRepository.save(item);
    }

    /**
     * 물품의 모든 입찰 내역 조회
     *
     * @param itemId 입찰 내역을 조회할 물품의 id
     * @return List<LightBidDto>
     */
    public List<LightBidDto> getBidHistories(UUID itemId) {
        // 물품 조회
        Item item = itemService.getItem(itemId);

        // 입찰 내역 리스트
        List<Bid> bids = item.getBids();

        // LightBidDto로 변환 후 반환
        return bids.stream().map(Bid::toLightBidDto).toList();
    }
}
