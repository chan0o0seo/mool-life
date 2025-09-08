package com.example.backend.auction.repository;

import com.example.backend.auction.model.Bid;
import com.example.backend.auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {

    @Query("""
            select distinct b.item from Bid b
            where b.bidder.email = :email
            """)
    List<Item> findDistinctItemsByBidderEmail(String email);

}
