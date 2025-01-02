package com.accounting.service;

import com.accounting.dto.AccountDTO;
import com.accounting.entity.Account;
import com.accounting.repository.AccountRepository;
import com.accounting.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserContextService userContextService;

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        Long clientId = userContextService.getCurrentClientId();
        return accountRepository.findAllActiveByClientId(clientId);
    }

    @Transactional
    public Account createAccount(AccountDTO dto) {
        Long clientId = userContextService.getCurrentClientId();
        
        if (accountRepository.existsByCodeAndClientId(dto.getCode(), clientId)) {
            throw new ValidationException("Account code already exists");
        }
        
        Account account = new Account();
        account.setClientId(clientId);
        account.setCode(dto.getCode());
        account.setName(dto.getName());
        account.setType(dto.getType());
        account.setGroupName(dto.getGroupName());
        account.setOpeningBalance(dto.getOpeningBalance());
        account.setCurrentBalance(dto.getOpeningBalance());
        
        return accountRepository.save(account);
    }
} 