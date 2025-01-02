package com.accounting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accounting.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.clientId = :clientId AND a.status = 'ACTIVE'")
    List<Account> findAllActiveByClientId(Long clientId);
    
    boolean existsByCodeAndClientId(String code, Long clientId);
    
    List<Account> findByClientIdAndStatus(Long clientId, String status);
} 