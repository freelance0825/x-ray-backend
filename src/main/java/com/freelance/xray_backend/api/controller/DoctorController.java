package com.freelance.xray_backend.api.controller;

import com.freelance.xray_backend.application.dto.doctor.DoctorRequestDto;
import com.freelance.xray_backend.application.dto.doctor.DoctorResponseDto;
import com.freelance.xray_backend.application.dto.doctor.LoginRequestDto;
import com.freelance.xray_backend.application.dto.doctor.LoginResponseDto;
import com.freelance.xray_backend.application.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<DoctorResponseDto> signupDoctor(@RequestBody @Valid DoctorRequestDto request) {
        log.info("Received doctor registration request with email: {}", request.getEmail());
        return doctorService.signupDoctor(request);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginDoctor(@RequestBody @Valid LoginRequestDto request) {
        log.info("Received doctor login request with phone number: {}", request.getEmail());
        return doctorService.login(request);

    }

}
