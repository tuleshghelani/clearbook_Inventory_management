package com.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Required;
import com.inventory.repository.PurchaseRepository;
import com.inventory.repository.SaleRepository;
import com.inventory.service.UtilityService;
import com.inventory.model.UserMaster;
import com.inventory.model.ApiResponse;
import com.inventory.exception.ValidationException;

@Service
@RequiredArgsConstructor
public class GstReportService {
    private final PurchaseRepository purchaseRepository;
    private final SaleRepository saleRepository;
    private final UtilityService utilityService;

    public ApiResponse<?> generateGstReport(String startDate, String endDate, String reportType) {
        UserMaster currentUser = utilityService.getCurrentLoggedInUser();
        
    }
} 