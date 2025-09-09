package com.example.backend.store.repository;

import com.example.backend.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {

    @Query("""
            select s from Store s
            where s.owner.email = :email
            """)
    List<Store> findByOwnerEmail(String email);
}
