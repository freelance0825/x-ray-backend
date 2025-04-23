package com.freelance.xray_backend.domain.mapper.patient;

import com.freelance.xray_backend.application.dto.patient.PatientRequestDto;
import com.freelance.xray_backend.application.dto.patient.PatientResponseDto;
import com.freelance.xray_backend.domain.entity.PatientEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(PatientMapperDecorator.class)
public interface PatientMapper {

    @Mapping(target = "imageBase64", ignore = true)
    PatientEntity toEntity(PatientRequestDto patientRequestDto);

    @Mapping(target = "imageBase64", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatientFromDto(PatientRequestDto dto, @MappingTarget PatientEntity entity);

    PatientResponseDto toDto(PatientEntity patientEntity);

    @Mapping(target = "imageBase64", source = "imageBase64")
    List<PatientResponseDto> toDtoList(List<PatientEntity> patientEntities);
}
