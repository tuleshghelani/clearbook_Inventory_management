package com.inventory.service;

import com.inventory.dto.ApiResponse;
import com.inventory.dto.GstSlabDto;
import com.inventory.entity.GstSlab;
import com.inventory.exception.ValidationException;
import com.inventory.repository.GstSlabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GstSlabService {
    private final GstSlabRepository gstSlabRepository;
    private final UtilityService utilityService;

    @Transactional
    public ApiResponse<?> create(GstSlabDto dto) {
        validateGstSlab(dto);
        
        if (gstSlabRepository.findByNameAndClientId(dto.getName(), utilityService.getCurrentLoggedInUser().getClient().getId()).isPresent()) {
            throw new ValidationException("GST slab with this name already exists");
        }
        
        GstSlab gstSlab = new GstSlab();
        mapDtoToEntity(dto, gstSlab);
        gstSlab.setClient(utilityService.getCurrentLoggedInUser().getClient());
        
        gstSlabRepository.save(gstSlab);
        return ApiResponse.success("GST slab created successfully");
    }
    
    private void validateGstSlab(GstSlabDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (dto.getCgstPercentage() == null || dto.getSgstPercentage() == null) {
            throw new ValidationException("CGST and SGST percentages are required");
        }
    }
    
    private void mapDtoToEntity(GstSlabDto dto, GstSlab entity) {
        entity.setName(dto.getName().trim());
        entity.setCgstPercentage(dto.getCgstPercentage());
        entity.setSgstPercentage(dto.getSgstPercentage());
        entity.setIgstPercentage(dto.getIgstPercentage());
        entity.setHsnCode(dto.getHsnCode());
        entity.setDescription(dto.getDescription());
    }
} 