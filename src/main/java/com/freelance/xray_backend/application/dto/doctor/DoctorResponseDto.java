package com.freelance.xray_backend.application.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DoctorResponseDto {

    private Long id;

    private String email;

    private String name;

    private String phoneNumber;

    private String specialist;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private String birthDate;

    private String token;
}
