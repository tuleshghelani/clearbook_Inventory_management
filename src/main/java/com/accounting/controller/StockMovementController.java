package com.accounting.controller;

import com.accounting.dto.ApiResponse;
import com.accounting.dto.StockMovementDTO;
import com.accounting.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService stockMovementService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllStockMovements() {
        try {
            return ResponseEntity.ok(ApiResponse.success(
                "Stock movements retrieved successfully", 
                stockMovementService.getAllStockMovements()
            ));
        } catch (Exception e) {
            log.error("Error in getAllStockMovements: ", e);
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error("Error retrieving stock movements"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createStockMovement(@RequestBody StockMovementDTO request) {
        try {
            return ResponseEntity.ok(ApiResponse.success(
                "Stock movement created successfully", 
                stockMovementService.createStockMovement(request)
            ));
        } catch (Exception e) {
            log.error("Error in createStockMovement: ", e);
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
} 