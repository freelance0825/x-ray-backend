package com.freelance.xray_backend.application.dto.caserecord;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CaseRecordsResponseDto {

    private Long id;

    private DoctorDto doctor;

    private PatientDto patient;

    private String todo;

    private String date;

    private String time;

    private String year;

    private String status ;

    private String type;

}
