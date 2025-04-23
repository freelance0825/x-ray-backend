package com.freelance.xray_backend.application.dto.caserecord;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatientDto {

    private Long id;

    private String name;

    private String age;

    private String gender;

    private String address;

    private String email;

    private String dateOfBirth;

    private String phoneNumber;

    private String imageBase64;

}
