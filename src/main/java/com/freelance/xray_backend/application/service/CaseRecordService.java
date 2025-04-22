package com.freelance.xray_backend.application.service;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordListResponseDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import com.freelance.xray_backend.domain.entity.DoctorEntity;
import com.freelance.xray_backend.domain.entity.PatientEntity;
import com.freelance.xray_backend.domain.mapper.CaseRecordMapper;
import com.freelance.xray_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.xray_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.xray_backend.infrastructure.port.PatientPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseRecordService {

    private final CaseRecordPersistencePort caseRecordPersistencePort;

    private final DoctorPersistencePort doctorPersistencePort;

    private final PatientPersistencePort patientPersistencePort;

    private final CaseRecordMapper caseRecordMapper;

    public CaseRecordListResponseDto saveCase(CaseRecordRequestDto request) throws IOException {

        DoctorEntity doctorEntity = doctorPersistencePort.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        PatientEntity patientEntity = patientPersistencePort.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Convert DTO to Entity using MapStruct
        CaseRecordEntity newCaseRecord = caseRecordMapper.toEntity(request);
        newCaseRecord.setDoctor(doctorEntity);
        newCaseRecord.setPatient(patientEntity);

        // Save Entity
        CaseRecordEntity savedCaseRecord = caseRecordPersistencePort.save(newCaseRecord);

        // Convert saved Entity to DTO
        return caseRecordMapper.toDto(savedCaseRecord);
    }

    public CaseRecordListResponseDto getCaseById (Long id) {
        CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(id).orElse(null);

        return caseRecordMapper.toDto(caseRecordEntity);
    }

    @Transactional
    public void deleteCase(Long id) throws IOException {
        log.info("Deleting case with ID: {}", id);

        if (!caseRecordPersistencePort.existsById(id)) {
            log.warn("Case with ID: {} not found. Deletion skipped.", id);
            throw new IllegalArgumentException("Slide not found with ID: " + id);
        }

        try {
            caseRecordPersistencePort.deleteById(id);
            log.info("Case with ID: {} has been deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting case with ID: {}: {}", id, e.getMessage());
            throw new IOException("Error deleting case: " + e.getMessage());
        }
    }

    public CaseRecordListResponseDto updateCase(Long id, CaseRecordRequestDto request) throws IOException {
        log.info("Updating case with ID: {}", id);
        try {
            CaseRecordEntity existingCaseRecord = caseRecordPersistencePort.findById(id).orElse(null);
            if (existingCaseRecord == null) {
                throw new IOException("Case not found for ID: " + id);
            }

            DoctorEntity doctorEntity = doctorPersistencePort.findById(request.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
            PatientEntity patientEntity = patientPersistencePort.findById(request.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

            // Convert DTO to Entity using MapStruct
            existingCaseRecord = caseRecordMapper.toEntity(request);
            existingCaseRecord.setDoctor(doctorEntity);
            existingCaseRecord.setPatient(patientEntity);

            // Save Entity
            CaseRecordEntity savedCaseRecord = caseRecordPersistencePort.save(existingCaseRecord);

            // Convert saved Entity to DTO
            return caseRecordMapper.toDto(savedCaseRecord);

        } catch (Exception e) {
            log.error("Error updating case: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public List<CaseRecordListResponseDto> getAllCases() {
        List<CaseRecordEntity> caseRecords = caseRecordPersistencePort.findAll();
        return caseRecords.stream()
                .map(caseRecordMapper::toDto)
                .collect(Collectors.toList());
    }

}
