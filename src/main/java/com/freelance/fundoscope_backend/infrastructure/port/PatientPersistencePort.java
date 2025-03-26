package com.freelance.fundoscope_backend.infrastructure.port;

import com.freelance.fundoscope_backend.domain.entity.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface PatientPersistencePort {

    Optional<PatientEntity> findById(Long id);

    PatientEntity save(PatientEntity patientEntity);

    List<PatientEntity> findAll();

    void deleteById(Long id);

}
