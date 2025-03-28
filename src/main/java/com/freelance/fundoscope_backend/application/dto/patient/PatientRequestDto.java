package com.freelance.fundoscope_backend.application.dto.patient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatientRequestDto {

    private Long id;

    private String name;

    private String age;

    private String gender;

    private String state;

    private String status;

    private String type;

    private String email;

    private String address;

    private String dateOfBirth;

    private String phoneNumber;

    private String imageBase64;

}
