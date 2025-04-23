package com.freelance.xray_backend.application.dto.caserecord;

import lombok.Data;

@Data
public class CaseRecordRequestDto {

    private Long doctorId;

    private Long patientId;

    private String todo;

    private String date;

    private String time;

    private String year;

    private String status;

    private String type;

}
