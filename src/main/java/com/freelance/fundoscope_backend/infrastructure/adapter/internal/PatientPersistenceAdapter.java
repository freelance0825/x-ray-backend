package com.freelance.fundoscope_backend.infrastructure.adapter.internal;

import com.freelance.fundoscope_backend.domain.entity.PatientEntity;
import com.freelance.fundoscope_backend.domain.repository.PatientRepository;
import com.freelance.fundoscope_backend.infrastructure.port.PatientPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PatientPersistenceAdapter implements PatientPersistencePort {

    private final PatientRepository patientRepository;

    @Override
    public Optional<PatientEntity> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public PatientEntity save(PatientEntity patientEntity) {
        return patientRepository.save(patientEntity);
    }

    @Override
    public List<PatientEntity> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

}
