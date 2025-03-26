package com.freelance.fundoscope_backend.application.dto.slide;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlideRequestDto {

    private Long caseRecordId;

    private String mainImage;

    private String qrCode;

    private String diagnosis;

    private String aiInsights;

    private String specimenType;

    private String clinicalData;

    private String collectionSite;

    private String reportId;

}
