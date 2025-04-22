package com.freelance.xray_backend.application.dto.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {

    private Long id;

    private String email;

    private String name;

    private String signature;

    private String token;

}
