package com.freelance.xray_backend.application.dto.doctor;

import lombok.Data;

@Data
public class LoginResponseDto {

    private Long id;

    private String email;

    private String name;

    private String signature;

    private String token;

}
