package com.inventory.controller;

import com.inventory.dto.ApiResponse;
import com.inventory.dto.GstSlabDto;
import com.inventory.service.GstSlabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gst-slabs")
public class GstSlabController {
    private final GstSlabService gstSlabService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody GstSlabDto dto) {
        return ResponseEntity.ok(gstSlabService.create(dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> search(GstSlabDto dto) {
        return ResponseEntity.ok(gstSlabService.search(dto));
    }

    @GetMapping("/report")
    public ResponseEntity<ApiResponse<?>> getGstReport(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String reportType) {
        return ResponseEntity.ok(gstSlabService.generateGstReport(startDate, endDate, reportType));
    }
} 