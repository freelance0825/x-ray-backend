package com.freelance.fundoscope_backend.application.dto.caserecord;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CaseRecordListRequestDto {

    private Long doctorId;

    private Long patientId;

    private String date;

    private String time;

    private String year;

    private String status;

    private String type;

}
