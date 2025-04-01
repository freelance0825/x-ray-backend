package com.freelance.fundoscope_backend.application.dto.slide;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SlideRequestDto {

    private Long caseRecordId;

    private MultipartFile mainImage;

    private MultipartFile qrCode;

    private String diagnosis;

    private String microscopicDc;

    private String aiInsights;

    private String specimenType;

    private String clinicalData;

    private String collectionSite;

    private String reportId;

    private MultipartFile doctorSignature;

}
