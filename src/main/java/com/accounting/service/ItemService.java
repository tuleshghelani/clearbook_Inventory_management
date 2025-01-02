package com.accounting.service;

import com.accounting.dto.ItemDTO;
import com.accounting.entity.Item;
import com.accounting.entity.ItemCategory;
import com.accounting.exception.ValidationException;
import com.accounting.repository.ItemRepository;
import com.accounting.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository categoryRepository;
    private final UtilityService utilityService;

    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        Long clientId = utilityService.getCurrentLoggedInUser().getClient().getId();
        return itemRepository.findAllActiveByClientId(clientId);
    }

    @Transactional
    public Item createItem(ItemDTO dto) {
        validateItemDTO(dto);
        Long clientId = utilityService.getCurrentLoggedInUser().getClient().getId();
        
        ItemCategory category = categoryRepository.findById(dto.getCategoryId())
            .orElseThrow(() -> new ValidationException("Category not found", HttpStatus.NOT_FOUND));
            
        itemRepository.findByCodeAndClientId(dto.getCode(), clientId)
            .ifPresent(i -> {
                throw new ValidationException("Item code already exists", HttpStatus.BAD_REQUEST);
            });
        
        Item item = new Item();
        item.setClientId(clientId);
        item.setCode(dto.getCode());
        item.setName(dto.getName());
        item.setCategory(category);
        item.setDescription(dto.getDescription());
        item.setUnit(dto.getUnit());
        item.setHsnCode(dto.getHsnCode());
        item.setPurchasePrice(dto.getPurchasePrice());
        item.setSalePrice(dto.getSalePrice());
        item.setMinimumStock(dto.getMinimumStock());
        item.setCurrentStock(dto.getCurrentStock());
        
        return itemRepository.save(item);
    }

    private void validateItemDTO(ItemDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ValidationException("Item name is required", HttpStatus.BAD_REQUEST);
        }
        if (dto.getCode() == null || dto.getCode().trim().isEmpty()) {
            throw new ValidationException("Item code is required", HttpStatus.BAD_REQUEST);
        }
        if (dto.getCategoryId() == null) {
            throw new ValidationException("Category is required", HttpStatus.BAD_REQUEST);
        }
        if (dto.getPurchasePrice() != null && dto.getPurchasePrice().signum() < 0) {
            throw new ValidationException("Purchase price cannot be negative", HttpStatus.BAD_REQUEST);
        }
        if (dto.getSalePrice() != null && dto.getSalePrice().signum() < 0) {
            throw new ValidationException("Sale price cannot be negative", HttpStatus.BAD_REQUEST);
        }
    }
} 