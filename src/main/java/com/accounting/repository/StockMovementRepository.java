package com.accounting.repository;

import com.accounting.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.clientId = :clientId AND sm.status = 'A' " +
           "ORDER BY sm.createdAt DESC")
    List<StockMovement> findAllByClientIdOrderByCreatedAtDesc(Long clientId);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.clientId = :clientId " +
           "AND sm.item.id = :itemId AND sm.status = 'A' ORDER BY sm.createdAt DESC")
    List<StockMovement> findByItemId(Long clientId, Long itemId);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.clientId = :clientId " +
           "AND sm.referenceType = :referenceType AND sm.referenceId = :referenceId " +
           "AND sm.status = 'A'")
    List<StockMovement> findByReference(Long clientId, String referenceType, Long referenceId);
} 