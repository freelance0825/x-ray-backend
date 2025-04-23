package com.freelance.xray_backend.domain.mapper.caseRecord;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordsResponseDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CaseRecordMapper {

    CaseRecordsResponseDto toDto(CaseRecordEntity caseRecordEntity);

    CaseRecordEntity toEntity(CaseRecordRequestDto caseRecordRequestDto);

    List<CaseRecordsResponseDto> toDtoList (List<CaseRecordEntity> caseRecordEntities);
}