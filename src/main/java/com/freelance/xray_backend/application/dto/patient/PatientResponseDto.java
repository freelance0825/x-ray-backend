package com.freelance.xray_backend.application.dto.patient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatientResponseDto {

    private Long id;

    private String name;

    private String age;

    private String gender;

    private String address;

    private String email;

    private String state;

    private String status;

    private String type;

    private String dateOfBirth;

    private String phoneNumber;

    private String imageBase64;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

}
