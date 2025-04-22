package com.freelance.xray_backend.domain.mapper;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordListResponseDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaseRecordMapper {

    // Convert Entity to Response DTO
    CaseRecordListResponseDto toDto(CaseRecordEntity caseRecordEntity);

    // Convert Request DTO to Entity
    CaseRecordEntity toEntity(CaseRecordRequestDto caseRecordRequestDto);

}
