package com.freelance.xray_backend.domain.mapper.slide;

import com.freelance.xray_backend.application.dto.slide.SlideRequestDto;
import com.freelance.xray_backend.application.dto.slide.SlideResponseDto;
import com.freelance.xray_backend.domain.entity.SlideEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(SlideMapperDecorator.class)
public interface SlideMapper {

    @Mapping(target = "caseRecord", source = "caseRecord")
    SlideResponseDto toDto(SlideEntity slideEntity);

    @Mapping(target = "caseRecord", source = "caseRecord")
    List<SlideResponseDto> toDtoList(List<SlideEntity> slideEntities);

    @Mapping(target = "mainImage", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    SlideEntity toEntity (SlideRequestDto requestDto);

    @Mapping(target = "mainImage", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSlidesFromDto (SlideRequestDto dto, @MappingTarget SlideEntity entity);
}
