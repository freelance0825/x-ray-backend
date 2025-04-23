package com.freelance.xray_backend.domain.mapper.patient;

import com.freelance.xray_backend.application.dto.patient.PatientRequestDto;
import com.freelance.xray_backend.application.dto.patient.PatientResponseDto;
import com.freelance.xray_backend.domain.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class PatientMapperDecorator implements PatientMapper {

    @Autowired
    @Qualifier("delegate")
    private PatientMapper delegate;

    @Override
    public PatientEntity toEntity(PatientRequestDto patientRequestDto) {
        if (patientRequestDto == null) {
            return null;
        }

        PatientEntity patientEntity = delegate.toEntity(patientRequestDto);
        patientEntity.setDateOfBirth((patientRequestDto.getDob() != null && !patientRequestDto.getDob().isEmpty()) ? patientRequestDto.getDob() : null);
        patientEntity.setImageBase64(patientRequestDto.getImageBase64() != null ? encodeImageToBase64(patientRequestDto.getImageBase64()) : null);

        return patientEntity;
    }


    @Override
    public void updatePatientFromDto(PatientRequestDto dto, PatientEntity entity) {
        if (dto == null) {
            return;
        }

        delegate.updatePatientFromDto(dto, entity);
        entity.setDateOfBirth(dto.getDob() != null && !dto.getDob().isEmpty() ? dto.getDob() : entity.getDateOfBirth());
        entity.setImageBase64(dto.getImageBase64() != null ? encodeImageToBase64(dto.getImageBase64()) : null);
    }

    @Override
    public PatientResponseDto toDto(PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }

        PatientResponseDto patientResponseDto = delegate.toDto(patientEntity);
        patientResponseDto.setImageBase64(patientEntity.getImageBase64());

        return patientResponseDto;
    }

    // Helper method to encode MultipartFile to Base64 string
    private String encodeImageToBase64(MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            return java.util.Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode image", e);
        }
    }
}