package com.freelance.fundoscope_backend.domain.mapper;

import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SlideMapper {

    @Mapping(source = "caseRecord", target = "caseRecord")
    @Mapping(source = "caseRecord.doctor", target = "caseRecord.doctor")
    @Mapping(source = "caseRecord.patient", target = "caseRecord.patient")
    SlideResponseDto toDto(SlideEntity slideEntity);

    List<SlideResponseDto> toDtoList(List<SlideEntity> slideEntities);
}
