package com.accounting.repository;

import com.accounting.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    @Query("SELECT c FROM ItemCategory c WHERE c.clientId = :clientId AND c.status = 'A'")
    List<ItemCategory> findAllActiveByClientId(Long clientId);
    
    Optional<ItemCategory> findByCodeAndClientId(String code, Long clientId);
} 