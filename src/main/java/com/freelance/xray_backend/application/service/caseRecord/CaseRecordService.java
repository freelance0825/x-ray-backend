package com.freelance.xray_backend.application.service.caseRecord;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordsResponseDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import com.freelance.xray_backend.domain.entity.DoctorEntity;
import com.freelance.xray_backend.domain.entity.PatientEntity;
import com.freelance.xray_backend.domain.mapper.caseRecord.CaseRecordMapper;
import com.freelance.xray_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.xray_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.xray_backend.infrastructure.port.PatientPersistencePort;
import com.freelance.xray_backend.shared.exception.CaseRecordNotFoundException;
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

    public CaseRecordsResponseDto saveCaseRecord(CaseRecordRequestDto request) {

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

    public CaseRecordsResponseDto updateCaseRecord(Long id, CaseRecordRequestDto request) {
        log.info("Updating case with ID: {}", id);

        try {
            CaseRecordEntity existingCaseRecord = caseRecordPersistencePort.findById(id)
                    .orElseThrow(() -> new CaseRecordNotFoundException("Case record not found for ID: " + id));

            DoctorEntity doctorEntity = doctorPersistencePort.findById(request.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
            PatientEntity patientEntity = patientPersistencePort.findById(request.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

            CaseRecordEntity updatedCaseRecord = caseRecordMapper.toEntity(request);
            updatedCaseRecord.setDoctor(doctorEntity);
            updatedCaseRecord.setPatient(patientEntity);

            return caseRecordMapper.toDto(caseRecordPersistencePort.save(updatedCaseRecord));
        } catch (Exception e) {
            log.error("Error updating case: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public CaseRecordsResponseDto getCaseRecordById(Long id) {
        CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(id).orElse(null);

        return caseRecordMapper.toDto(caseRecordEntity);
    }

    @Transactional
    public void deleteCaseRecord(Long id){
        log.info("Deleting case with ID: {}", id);

        try {
            CaseRecordEntity existingCaseRecord = caseRecordPersistencePort.findById(id)
                    .orElseThrow(() -> new CaseRecordNotFoundException("Case record not found for ID: " + id));

            caseRecordPersistencePort.deleteById(id);
            log.info("Case record with ID: {} has been deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting case record with ID: {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting case record: " + e.getMessage());
        }
    }

    public List<CaseRecordsResponseDto> getAllCaseRecords() {
        List<CaseRecordEntity> caseRecordList = caseRecordPersistencePort.findAll();
        return caseRecordMapper.toDtoList(caseRecordList);
    }

}
