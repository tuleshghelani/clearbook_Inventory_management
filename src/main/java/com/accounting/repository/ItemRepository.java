package com.accounting.repository;

import com.accounting.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.clientId = :clientId AND i.status = 'A'")
    List<Item> findAllActiveByClientId(Long clientId);
    
    Optional<Item> findByCodeAndClientId(String code, Long clientId);
    
    @Query("SELECT i FROM Item i WHERE i.clientId = :clientId AND i.status = 'A' " +
           "AND (LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(i.code) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Item> searchItems(String search, Long clientId);
} 