package com.freelance.fundoscope_backend.application.dto.caserecord;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CaseRecordListResponseDto {

    private Long id;

    private DoctorDto doctor;

    private PatientDto patient;

    private String date;

    private String time;

    private String year;

    private String status ;

    private String type;




}
