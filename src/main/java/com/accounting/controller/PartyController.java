package com.accounting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accounting.dto.ApiResponse;
import com.accounting.dto.PartyDTO;
import com.accounting.service.PartyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllParties() {
        return ResponseEntity.ok(ApiResponse.success("Parties retrieved successfully", 
            partyService.getAllParties()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createParty(@RequestBody PartyDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Party created successfully", 
            partyService.createParty(request)));
    }
} 