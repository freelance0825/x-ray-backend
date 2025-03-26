package com.freelance.fundoscope_backend.domain.mapper;

import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SlideMapper {

    SlideResponseDto toDto(SlideEntity slideEntity);
}
