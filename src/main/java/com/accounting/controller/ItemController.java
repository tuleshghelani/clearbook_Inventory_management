package com.accounting.controller;

import com.accounting.dto.ApiResponse;
import com.accounting.dto.ItemDTO;
import com.accounting.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllItems() {
        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", 
            itemService.getAllItems()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createItem(@RequestBody ItemDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Item created successfully", 
            itemService.createItem(request)));
    }
} 