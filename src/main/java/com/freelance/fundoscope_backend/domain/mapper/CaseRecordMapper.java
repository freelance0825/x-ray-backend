package com.freelance.fundoscope_backend.domain.mapper;

import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordListResponseDto;
import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaseRecordMapper {

    // Convert Entity to Response DTO
    CaseRecordListResponseDto toDto(CaseRecordEntity caseRecordEntity);

    // Convert Request DTO to Entity
    CaseRecordEntity toEntity(CaseRecordRequestDto caseRecordRequestDto);

}
