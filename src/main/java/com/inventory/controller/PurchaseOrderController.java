package com.inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody PurchaseOrderDto request) {
        return ResponseEntity.ok(purchaseOrderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> search(PurchaseOrderDto request) {
        return ResponseEntity.ok(purchaseOrderService.search(request));
    }

    @PostMapping("/{id}/convert-to-purchase")
    public ResponseEntity<ApiResponse<?>> convertToPurchase(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.convertToPurchase(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<?>> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.cancel(id));
    }
} 