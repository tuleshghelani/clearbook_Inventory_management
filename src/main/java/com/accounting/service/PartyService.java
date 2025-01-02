package com.accounting.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accounting.dto.PartyDTO;
import com.accounting.entity.Party;
import com.accounting.exception.ValidationException;
import com.accounting.repository.PartyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final UtilityService utilityService;

    @Transactional(readOnly = true)
    public List<Party> getAllParties() {
        Long clientId = utilityService.getCurrentLoggedInUser().getClient().getId();
        return partyRepository.findAllActiveByClientId(clientId);
    }

    @Transactional
    public Party createParty(PartyDTO dto) {
        Long clientId = utilityService.getCurrentLoggedInUser().getClient().getId();
        
        partyRepository.findByCodeAndClientId(dto.getCode(), clientId)
            .ifPresent(p -> {
                throw new ValidationException("Party code already exists", HttpStatus.BAD_REQUEST);
            });
        
        Party party = new Party();
        party.setClientId(clientId);
        party.setName(dto.getName());
        party.setCode(dto.getCode());
        party.setAddress(dto.getAddress());
        party.setPhone(dto.getPhone());
        party.setEmail(dto.getEmail());
        party.setGstNumber(dto.getGstNumber());
        party.setPanNumber(dto.getPanNumber());
        party.setOpeningBalance(dto.getOpeningBalance());
        party.setCurrentBalance(dto.getOpeningBalance());
        party.setCreditLimit(dto.getCreditLimit());
        
        return partyRepository.save(party);
    }
} 