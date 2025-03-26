package com.freelance.fundoscope_backend.application.service;

import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordListResponseDto;
import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import com.freelance.fundoscope_backend.domain.entity.DoctorEntity;
import com.freelance.fundoscope_backend.domain.entity.PatientEntity;
import com.freelance.fundoscope_backend.domain.mapper.CaseRecordMapper;
import com.freelance.fundoscope_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.fundoscope_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.fundoscope_backend.infrastructure.port.PatientPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        CaseRecordEntity caseRecordEntity = caseRecordMapper.toEntity(request);
        caseRecordEntity.setDoctor(doctorEntity);
        caseRecordEntity.setPatient(patientEntity);

        // Save Entity
        CaseRecordEntity savedCaseRecord = caseRecordPersistencePort.save(caseRecordEntity);

        // Convert saved Entity to DTO
        return caseRecordMapper.toDto(savedCaseRecord);
    }


    public List<CaseRecordListResponseDto> getAllCases() {
        List<CaseRecordEntity> caseRecords = caseRecordPersistencePort.findAll();
        return caseRecords.stream()
                .map(caseRecordMapper::toDto)
                .collect(Collectors.toList());
    }

}
