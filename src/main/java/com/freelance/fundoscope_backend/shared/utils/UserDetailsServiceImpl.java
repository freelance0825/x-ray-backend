package com.freelance.fundoscope_backend.shared.utils;

import com.freelance.fundoscope_backend.domain.entity.DoctorEntity;
import com.freelance.fundoscope_backend.infrastructure.port.DoctorPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DoctorPersistencePort doctorPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DoctorEntity doctorEntity = doctorPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(doctorEntity.getEmail())
                .password(doctorEntity.getPassword())
                .authorities("USER") // Add roles if needed
                .build();
    }
}
