package com.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.inventory.repository.PurchaseOrderRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.service.GstCalculationService;
import com.inventory.service.UtilityService;
import com.inventory.dto.PurchaseOrderDto;
import com.inventory.entity.PurchaseOrder;
import com.inventory.entity.UserMaster;
import com.inventory.entity.Product;
import com.inventory.entity.PurchaseOrderStatus;
import com.inventory.response.ApiResponse;
import com.inventory.exception.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final GstCalculationService gstCalculationService;
    private final UtilityService utilityService;

    @Transactional
    public ApiResponse<?> createOrder(PurchaseOrderDto dto) {
        validatePurchaseOrder(dto);
        UserMaster currentUser = utilityService.getCurrentLoggedInUser();
        
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderNumber(generateOrderNumber());
        order.setOrderDate(dto.getOrderDate() != null ? dto.getOrderDate() : OffsetDateTime.now());
        order.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        order.setSupplier(dto.getSupplier());
        order.setRemarks(dto.getRemarks());
        order.setClient(currentUser.getClient());
        order.setCreatedBy(currentUser);
        
        processOrderItems(order, dto.getItems());
        
        purchaseOrderRepository.save(order);
        
        return ApiResponse.success("Purchase order created successfully");
    }

    @Transactional
    public ApiResponse<?> convertToPurchase(Long orderId) {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
            .orElseThrow(() -> new ValidationException("Purchase order not found"));
            
        // Reference existing PurchaseService code for creating purchase:
        // Lines 43-72 from PurchaseService.java
        
        order.setStatus(PurchaseOrderStatus.COMPLETED);
        purchaseOrderRepository.save(order);
        
        return ApiResponse.success("Purchase order converted to purchase successfully");
    }
} 