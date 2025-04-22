package com.freelance.xray_backend.infrastructure.port;

import com.freelance.xray_backend.domain.entity.DoctorEntity;

import java.util.Optional;

public interface DoctorPersistencePort {

    Optional<DoctorEntity> findByEmail(String email);

    DoctorEntity save(DoctorEntity doctorEntity);

    boolean existsByEmail(String email);

    Optional<DoctorEntity> findById(Long id);

}
