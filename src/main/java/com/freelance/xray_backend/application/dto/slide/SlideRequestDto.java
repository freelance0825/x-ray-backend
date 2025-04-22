package com.freelance.xray_backend.application.dto.slide;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String microscopicDc;

    private String diagnosis;

    private String aiInsights;

    private String specimenType;

    private String collectionSite;

    private String reportId;

    private String clinicalData;

    private MultipartFile doctorSignature;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mma")
    private String dateAndTime;

}
