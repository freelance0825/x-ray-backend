package com.freelance.fundoscope_backend.domain.mapper;

import com.freelance.fundoscope_backend.application.dto.patient.PatientResponseDto;
import com.freelance.fundoscope_backend.domain.entity.PatientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientResponseDto toDto(PatientEntity patientEntity);

    List<PatientResponseDto> toDtoList(List<PatientEntity> patientEntities);

}
