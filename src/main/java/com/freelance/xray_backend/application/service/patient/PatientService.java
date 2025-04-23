package com.freelance.xray_backend.application.service.patient;

import com.freelance.xray_backend.application.dto.patient.PatientRequestDto;
import com.freelance.xray_backend.application.dto.patient.PatientResponseDto;
import com.freelance.xray_backend.domain.entity.PatientEntity;
import com.freelance.xray_backend.domain.mapper.patient.PatientMapper;
import com.freelance.xray_backend.infrastructure.port.PatientPersistencePort;
import com.freelance.xray_backend.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientPersistencePort patientPersistencePort;

    private final PatientMapper patientMapper;

    public PatientResponseDto savePatient(PatientRequestDto request) throws IOException {
        // Delegate the imageBase64 encoding to the mapper decorator
        PatientEntity patient = patientMapper.toEntity(request);

        return patientMapper.toDto(patientPersistencePort.save(patient));
    }

    public PatientResponseDto updateUser(Long id, PatientRequestDto request) throws IOException {
        log.info("Updating user with ID: {}", id);

        try {
            // Fetch existing user from the database
            PatientEntity existingUser = patientPersistencePort.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Patient not found for ID: " + id));

            // Use the mapper to update the existing entity with new values from the DTO
            patientMapper.updatePatientFromDto(request, existingUser);
            existingUser.setId(id);  // Preserve ID
            existingUser.setCaseRecords(existingUser.getCaseRecords());  // Preserve caseRecords

            // Save the updated patient entity
            return patientMapper.toDto(patientPersistencePort.save(existingUser));

        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            throw new IOException("Error updating user", e);
        }
    }

    public List<PatientResponseDto> getAllPatients() {
        List<PatientEntity> patientList = patientPersistencePort.findAll();
        return patientMapper.toDtoList(patientList);
    }

    @Transactional
    public void deletePatient(Long id) {
        log.info("Deleting patient with ID: {}", id);

        try {
            PatientEntity existingPatient = patientPersistencePort.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Patient not found for ID: " + id));

            patientPersistencePort.deleteById(id);
            log.info("Patient with ID: {} has been deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting patient with ID: {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting patient: " + e.getMessage());
        }
    }

}
