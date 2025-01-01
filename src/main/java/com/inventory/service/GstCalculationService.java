package com.inventory.service;

import com.inventory.entity.PurchaseItem;
import com.inventory.entity.GstSlab;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class GstCalculationService {
    
    public void calculateItemGst(PurchaseItem item, GstSlab gstSlab) {
        BigDecimal baseAmount = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        BigDecimal discountedAmount = calculateDiscountedAmount(baseAmount, item.getDiscount());
        item.setTaxableAmount(discountedAmount);
        
        // Calculate GST amounts
        item.setCgstPercentage(gstSlab.getCgstPercentage());
        item.setSgstPercentage(gstSlab.getSgstPercentage());
        item.setIgstPercentage(gstSlab.getIgstPercentage());
        
        item.setCgstAmount(calculateTaxAmount(discountedAmount, gstSlab.getCgstPercentage()));
        item.setSgstAmount(calculateTaxAmount(discountedAmount, gstSlab.getSgstPercentage()));
        item.setIgstAmount(calculateTaxAmount(discountedAmount, gstSlab.getIgstPercentage()));
        
        item.setTotalTaxAmount(item.getCgstAmount()
            .add(item.getSgstAmount())
            .add(item.getIgstAmount()));
            
        item.setTotalAmount(discountedAmount.add(item.getTotalTaxAmount()));
    }
    
    private BigDecimal calculateDiscountedAmount(BigDecimal amount, BigDecimal discountPercentage) {
        if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
            return amount;
        }
        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
            discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
        return amount.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);
    }
    
    private BigDecimal calculateTaxAmount(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            .setScale(2, RoundingMode.HALF_UP);
    }
} 