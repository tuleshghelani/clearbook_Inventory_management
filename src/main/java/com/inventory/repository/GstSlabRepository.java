package com.inventory.repository;

import com.inventory.entity.GstSlab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface GstSlabRepository extends JpaRepository<GstSlab, Long> {
    Optional<GstSlab> findByNameAndClientId(String name, Long clientId);
    
    @Query("SELECT g FROM GstSlab g WHERE g.client.id = :clientId AND g.name LIKE %:search%")
    List<GstSlab> searchByName(String search, Long clientId);
    
    List<GstSlab> findByClientId(Long clientId);
} 