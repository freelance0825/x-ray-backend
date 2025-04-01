package com.freelance.fundoscope_backend.application.dto.slide;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordListResponseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SlideResponseDto {

    private Long id;

    private CaseRecordListResponseDto caseRecord;

    private String mainImage;

    private String qrCode;

    private String diagnosis;

    private String microscopicDc;

    private String aiInsights;

    private String specimenType;

    private String collectionSite;

    private String clinicalData;

    private String reportId;

}
