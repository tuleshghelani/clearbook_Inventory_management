package com.inventory.service;

import com.inventory.dao.PurchaseDao;
import com.inventory.dto.ApiResponse;
import com.inventory.dto.PurchaseDto;
import com.inventory.dto.PurchaseItemDto;
import com.inventory.entity.*;
import com.inventory.exception.ValidationException;
import com.inventory.repository.PurchaseRepository;
import com.inventory.repository.PurchaseItemRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.CustomerRepository;
import com.inventory.util.DiscountCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final UtilityService utilityService;
    private final PurchaseDao purchaseDao;

    @Transactional
    public ApiResponse<?> create(PurchaseDto dto) {
        validatePurchase(dto);
        UserMaster currentUser = utilityService.getCurrentLoggedInUser();
        
        Purchase purchase = new Purchase();
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setPurchaseDate(dto.getPurchaseDate() != null ? dto.getPurchaseDate() : OffsetDateTime.now());
        purchase.setCustomer(customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new ValidationException("Customer not found")));
        purchase.setRemarks(dto.getRemarks());
        purchase.setClient(currentUser.getClient());
        purchase.setCreatedBy(currentUser);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // Process each item
        for (PurchaseItemDto itemDto : dto.getItems()) {
            PurchaseItem item = createPurchaseItem(itemDto, purchase, currentUser);
//            purchase.getItems().add(item);
            totalAmount = totalAmount.add(item.getTotalAmount());
        }
        
        purchase.setTotalAmount(totalAmount);
        purchase = purchaseRepository.save(purchase);
        
        // Update quantities
        
        return ApiResponse.success("Purchase created successfully");
    }
    
    private PurchaseItem createPurchaseItem(PurchaseItemDto itemDto, Purchase purchase, UserMaster currentUser) {
        Product product = productRepository.findById(itemDto.getProductId())
            .orElseThrow(() -> new ValidationException("Product not found"));
            
        PurchaseItem item = new PurchaseItem();
        item.setPurchase(purchase);
        item.setProduct(product);
        item.setCategory(product.getCategory());
        item.setQuantity(itemDto.getQuantity());
        item.setUnitPrice(itemDto.getUnitPrice());
        item.setRemainingQuantity(itemDto.getQuantity());
        item.setClient(currentUser.getClient());
        
//        calculateItemAmounts(item, itemDto);
        
        return item;
    }

    public ApiResponse<List<PurchaseDto>> findAll() {
        try {
            UserMaster currentUser = utilityService.getCurrentLoggedInUser();
            List<Purchase> purchases = purchaseRepository.findByClientId(currentUser.getClient().getId());
            List<PurchaseDto> purchasesDto = purchases.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
            
            return ApiResponse.success("Purchases retrieved successfully", purchasesDto);
        } catch (Exception e) {
            throw new ValidationException("Failed to retrieve purchases");
        }
    }

    private void validatePurchase(PurchaseDto dto) {
        if (dto.getProductId() == null) {
            throw new ValidationException("Product is required");
        }
        
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new ValidationException("Valid quantity is required");
        }
        
        if (dto.getUnitPrice() == null || dto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Valid unit price is required");
        }
        
        if (!StringUtils.hasText(dto.getInvoiceNumber())) {
            throw new ValidationException("Invoice number is required");
        }
        
        if (dto.getOtherExpenses() != null && dto.getOtherExpenses().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Other expenses cannot be negative");
        }
        
        if (dto.getDiscount() != null) {
            if (dto.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Discount percentage cannot be negative");
            }
            if (dto.getDiscount().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new ValidationException("Discount percentage cannot be greater than 100");
            }
        }
    }

    private void calculateAmounts(Purchase purchase, PurchaseDto dto) {
        // Calculate base amount
        BigDecimal baseAmount = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        
        // Calculate discount amount
        BigDecimal discountAmount = dto.getDiscountAmount();
        
        // Calculate discounted price
        BigDecimal discountPrice = DiscountCalculator.calculateDiscountedPrice(baseAmount, discountAmount);
        
        // Calculate total amount including other expenses
        BigDecimal totalAmount = DiscountCalculator.calculateTotalAmount(discountPrice, dto.getOtherExpenses());
        
        // Set all calculated values
        purchase.setDiscount(dto.getDiscount());
        purchase.setDiscountAmount(discountAmount);
        purchase.setDiscountPrice(discountPrice);
        purchase.setTotalAmount(totalAmount);
    }

    private PurchaseDto mapToDto(Purchase purchase) {
        PurchaseDto dto = new PurchaseDto();
        dto.setId(purchase.getId());
        dto.setTotalAmount(purchase.getTotalAmount());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        dto.setInvoiceNumber(purchase.getInvoiceNumber());
        dto.setOtherExpenses(purchase.getOtherExpenses());
        dto.setClientId(purchase.getClient().getId());
        return dto;
    }

//    public Page<Map<String, Object>> searchPurchases(PurchaseDto dto) {
//        try {
//            UserMaster currentUser = utilityService.getCurrentLoggedInUser();
//            dto.setClientId(currentUser.getClient().getId());
//            return purchaseDao.searchPurchases(dto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ValidationException("Failed to search purchases");
//        }
//    }

}
