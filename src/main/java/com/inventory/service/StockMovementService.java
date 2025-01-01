package com.inventory.service;

import com.inventory.entity.*;
import com.inventory.enums.MovementType;
import com.inventory.repository.StockMovementRepository;
import com.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final UtilityService utilityService;

    @Transactional
    public void recordPurchaseMovement(PurchaseItem purchaseItem) {
        StockMovement movement = new StockMovement();
        movement.setProduct(purchaseItem.getProduct());
        movement.setMovementDate(purchaseItem.getPurchase().getPurchaseDate());
        movement.setMovementType(MovementType.IN);
        movement.setQuantity(purchaseItem.getQuantity());
        movement.setUnitPrice(purchaseItem.getUnitPrice());
        movement.setReferenceType("PURCHASE");
        movement.setReferenceId(purchaseItem.getPurchase().getId());
        movement.setClient(utilityService.getCurrentLoggedInUser().getClient());
        movement.setRemarks("Purchase: " + purchaseItem.getPurchase().getInvoiceNumber());
        
        stockMovementRepository.save(movement);
        updateProductStock(purchaseItem.getProduct().getId(), purchaseItem.getQuantity(), true);
    }

    private void updateProductStock(Long productId, Integer quantity, boolean isAddition) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
            
        if (isAddition) {
            product.setRemainingQuantity(product.getRemainingQuantity() + quantity);
        } else {
            product.setRemainingQuantity(product.getRemainingQuantity() - quantity);
        }
        
        productRepository.save(product);
    }
} 