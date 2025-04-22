package com.freelance.xray_backend.infrastructure.adapter.internal;

import com.freelance.xray_backend.domain.entity.DoctorEntity;
import com.freelance.xray_backend.domain.repository.DoctorRepository;
import com.freelance.xray_backend.infrastructure.port.DoctorPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DoctorPersistenceAdapter implements DoctorPersistencePort {

    private final DoctorRepository doctorRepository;

    @Override
    public Optional<DoctorEntity> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public DoctorEntity save(DoctorEntity doctorEntity) {
        return doctorRepository.save(doctorEntity);  // Return the saved entity
    }

    @Override
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);  // Returns true if email exists, false otherwise
    }

    @Override
    public Optional<DoctorEntity> findById(Long id) {
        return doctorRepository.findById(id);
    }

}
