package com.accounting.security;

import com.accounting.entity.UserMaster;
import com.accounting.repository.UserRepository;
import com.accounting.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserMaster userMaster = userRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException("User not found", HttpStatus.UNAUTHORIZED));

        return UserPrincipal.create(userMaster);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id) {
        UserMaster userMaster = userRepository.findById(id)
                .orElseThrow(() -> new ValidationException("User not found", HttpStatus.UNAUTHORIZED));

        return UserPrincipal.create(userMaster);
    }
}