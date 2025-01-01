package com.inventory.dao;

import com.inventory.dto.PurchaseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class PurchaseDao {
    private final EntityManager entityManager;
    
    public PurchaseDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
//    public Map<String, Object> searchPurchases(PurchaseDto dto) {
//        StringBuilder sql = new StringBuilder();
//        Map<String, Object> params = new HashMap<>();
//
//        sql.append("""
//            SELECT p.id, p.invoice_number, p.purchase_date,
//                   c.id as customer_id, c.name as customer_name,
//                   COUNT(pi.id) as total_items,
//                   p.total_amount, p.total_discount, p.total_other_expenses,
//                   p.net_amount, p.payment_status, p.payment_mode,
//                   p.payment_reference
//            FROM purchase p
//            LEFT JOIN customer c ON p.customer_id = c.id
//            LEFT JOIN purchase_item pi ON p.id = pi.purchase_id
//            WHERE p.client_id = :clientId
//        """);
//
//        // Add search conditions
//        appendSearchConditions(sql, params, dto);
//
//        // Add date range filter
//        addDateRangeFilter(sql, params, dto);
//
//        sql.append(" GROUP BY p.id, c.id");
//
//        // Add sorting
//        addSorting(sql, dto);
//
//        // Execute query and map results
//        return executeSearchQuery(sql.toString(), params, dto);
//    }

}