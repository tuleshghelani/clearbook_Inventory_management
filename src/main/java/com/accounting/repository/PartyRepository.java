package com.accounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accounting.entity.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    @Query("SELECT p FROM Party p WHERE p.clientId = :clientId AND p.status = 'A'")
    List<Party> findAllActiveByClientId(Long clientId);
    
    Optional<Party> findByCodeAndClientId(String code, Long clientId);
    
    @Query("SELECT p FROM Party p WHERE p.clientId = :clientId AND p.status = 'A' " +
           "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Party> searchParties(String search, Long clientId);
} 