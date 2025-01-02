package com.accounting.service;

import com.accounting.dto.StockMovementDTO;
import com.accounting.entity.StockMovement;
import com.accounting.entity.Item;
import com.accounting.exception.ValidationException;
import com.accounting.repository.StockMovementRepository;
import com.accounting.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ItemRepository itemRepository;
    private final UtilityService utilityService;

    @Transactional(readOnly = true)
    public List<StockMovement> getAllStockMovements() {
        try {
            Long clientId = utilityService.getCurrentLoggedInUser().getClient().getId();
            return stockMovementRepository.findAllByClientIdOrderByCreatedAtDesc(clientId);
        } catch (Exception e) {
            log.error("Error in getAllStockMovements: ", e);
            e.printStackTrace();
            throw new ValidationException("Error retrieving stock movements", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public StockMovement createStockMovement(StockMovementDTO dto) {
        try {
            validateStockMovementDTO(dto);
            Long clientId = utilityService.getCurrentLoggedInUser().getClientId();
            
            Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ValidationException("Item not found", HttpStatus.NOT_FOUND));

            StockMovement movement = new StockMovement();
            movement.setClientId(clientId);
            movement.setItem(item);
            movement.setReferenceType(dto.getReferenceType());
            movement.setReferenceId(dto.getReferenceId());
            movement.setQuantity(dto.getQuantity());
            movement.setUnitPrice(dto.getUnitPrice());
            movement.setRemarks(dto.getRemarks());
            
            updateItemStock(item, dto.getQuantity(), dto.getReferenceType());
            
            return stockMovementRepository.save(movement);
        } catch (ValidationException ve) {
            log.error("Validation error in createStockMovement: ", ve);
            ve.printStackTrace();
            throw ve;
        } catch (Exception e) {
            log.error("Error in createStockMovement: ", e);
            e.printStackTrace();
            throw new ValidationException("Error creating stock movement", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateStockMovementDTO(StockMovementDTO dto) {
        if (dto.getItemId() == null) {
            throw new ValidationException("Item is required", HttpStatus.BAD_REQUEST);
        }
        if (dto.getQuantity() == null || dto.getQuantity().signum() == 0) {
            throw new ValidationException("Valid quantity is required", HttpStatus.BAD_REQUEST);
        }
        if (dto.getReferenceType() == null || dto.getReferenceType().trim().isEmpty()) {
            throw new ValidationException("Reference type is required", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void updateItemStock(Item item, BigDecimal quantity, String referenceType) {
        if ("PURCHASE".equals(referenceType)) {
            item.setCurrentStock(item.getCurrentStock().add(quantity));
        } else if ("SALE".equals(referenceType)) {
            if (item.getCurrentStock().compareTo(quantity) < 0) {
                throw new ValidationException("Insufficient stock", HttpStatus.BAD_REQUEST);
            }
            item.setCurrentStock(item.getCurrentStock().subtract(quantity));
        }
        itemRepository.save(item);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StockMovement> getStockMovementsByItem(Long itemId) {
        try {
            Long clientId = utilityService.getCurrentLoggedInUser().getClientId();
            return stockMovementRepository.findByItemId(clientId, itemId);
        } catch (Exception e) {
            log.error("Error in getStockMovementsByItem: ", e);
            e.printStackTrace();
            throw new ValidationException("Error retrieving stock movements for item", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StockMovement> getStockMovementsByReference(String referenceType, Long referenceId) {
        try {
            Long clientId = utilityService.getCurrentLoggedInUser().getClientId();
            return stockMovementRepository.findByReference(clientId, referenceType, referenceId);
        } catch (Exception e) {
            log.error("Error in getStockMovementsByReference: ", e);
            e.printStackTrace();
            throw new ValidationException("Error retrieving stock movements for reference", 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 