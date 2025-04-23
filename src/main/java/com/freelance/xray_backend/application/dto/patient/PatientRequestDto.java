package com.freelance.xray_backend.application.dto.patient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PatientRequestDto {

    private String name;

    private String address;

    private String gender;

    private String email;

    private String state;

    private String status;

    private String type;

    private String age;

    private String dob;

    private String phoneNumber;

    private MultipartFile image;

}
