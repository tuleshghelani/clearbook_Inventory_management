package com.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.inventory.entity.PurchaseReturn;
import com.inventory.entity.PurchaseReturnItem;
import com.inventory.entity.Purchase;
import com.inventory.entity.PurchaseItem;
import com.inventory.entity.PurchaseItemDto;
import com.inventory.entity.PurchaseReturnDto;
import com.inventory.entity.PurchaseReturnItemDto;
import com.inventory.repository.PurchaseReturnRepository;
import com.inventory.repository.PurchaseRepository;
import com.inventory.service.StockMovementService;
import com.inventory.service.GstCalculationService;
import com.inventory.service.UtilityService;
import com.inventory.entity.ApiResponse;
import com.inventory.exception.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseReturnService {
    private final PurchaseReturnRepository purchaseReturnRepository;
    private final PurchaseRepository purchaseRepository;
    private final StockMovementService stockMovementService;
    private final GstCalculationService gstCalculationService;
    private final UtilityService utilityService;

    @Transactional
    public ApiResponse<?> createReturn(PurchaseReturnDto dto) {
        Purchase purchase = purchaseRepository.findById(dto.getPurchaseId())
            .orElseThrow(() -> new ValidationException("Purchase not found"));
            
        validateReturn(dto, purchase);
        
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setPurchase(purchase);
        purchaseReturn.setReturnDate(dto.getReturnDate() != null ? dto.getReturnDate() : OffsetDateTime.now());
        purchaseReturn.setReturnNumber(generateReturnNumber());
        purchaseReturn.setRemarks(dto.getRemarks());
        purchaseReturn.setClient(utilityService.getCurrentLoggedInUser().getClient());
        purchaseReturn.setCreatedBy(utilityService.getCurrentLoggedInUser());
        
        processReturnItems(purchaseReturn, dto.getItems());
        
        purchaseReturnRepository.save(purchaseReturn);
        
        // Update stock and create movements
        purchaseReturn.getItems().forEach(item -> {
            stockMovementService.recordReturnMovement(item);
        });
        
        return ApiResponse.success("Purchase return created successfully");
    }
    
    private void processReturnItems(PurchaseReturn purchaseReturn, List<PurchaseReturnItemDto> items) {
        // Implementation for processing return items
        // Reference existing code:
    }
} 