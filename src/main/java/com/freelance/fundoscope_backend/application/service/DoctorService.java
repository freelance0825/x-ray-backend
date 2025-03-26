package com.freelance.fundoscope_backend.application.service;

import com.freelance.fundoscope_backend.application.dto.doctor.DoctorRequestDto;
import com.freelance.fundoscope_backend.application.dto.doctor.DoctorResponseDto;
import com.freelance.fundoscope_backend.application.dto.doctor.LoginRequestDto;
import com.freelance.fundoscope_backend.application.dto.doctor.LoginResponseDto;
import com.freelance.fundoscope_backend.domain.entity.DoctorEntity;
import com.freelance.fundoscope_backend.domain.mapper.DoctorMapper;
import com.freelance.fundoscope_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.fundoscope_backend.shared.config.JwtUtil;
import com.freelance.fundoscope_backend.shared.exception.EmailAlreadyExistException;
import com.freelance.fundoscope_backend.shared.exception.InvalidCredentialsException;
import com.freelance.fundoscope_backend.shared.exception.InvalidRequestException;
import com.freelance.fundoscope_backend.shared.exception.UserNotFoundException;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorPersistencePort doctorPersistencePort;

    private final JwtUtil jwtUtil;

    private final DoctorMapper doctorMapper;  // Inject the MapStruct mapper

    private final PasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Description("Convert image to Base64 and save the doctor details.")
    public ResponseEntity<DoctorResponseDto> signupDoctor(DoctorRequestDto request) {

        if (doctorPersistencePort.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistException(request.getEmail());
        }

        // Hash the password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setName(request.getName());
        doctorEntity.setEmail(request.getEmail());
        doctorEntity.setPassword(hashedPassword);
        doctorEntity.setPhoneNumber(request.getPhoneNumber());
        doctorEntity.setSpecialist(request.getSpecialist());
        doctorEntity.setBirthDate(request.getBirthDate());

        String token = jwtUtil.generateToken(doctorEntity.getEmail());

        DoctorEntity savedDoctorEntity = doctorPersistencePort.save(doctorEntity);
        DoctorResponseDto response = doctorMapper.mapToDto(savedDoctorEntity);
        response.setToken(token);

        return ResponseEntity.ok(response);
    }


    @Description("Doctors Login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto request) {

        if (ObjectUtils.isEmpty(request)) {
            throw new InvalidRequestException("Email and password must be provided.");
        }

        log.info("Attempting to find user by email: {}", request.getEmail());

        Optional<DoctorEntity> doctorOptional = doctorPersistencePort.findByEmail(request.getEmail());

        if (doctorOptional.isEmpty()) {
            String errorMessage = "User not found with email: " + request.getEmail();
            log.warn(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        DoctorEntity doctorEntity = doctorOptional.get();
        log.info("User found: {}", doctorEntity.getEmail());

        // Compare passwords using BCrypt (this since password is hashed)
        if (BCrypt.checkpw(request.getPassword(), doctorEntity.getPassword())) {
            log.info("Password is correct for user: {}", request.getEmail());
        } else {
            String errorMessage = "Incorrect password for user with email: " + request.getEmail();
            log.warn(errorMessage);
            throw new InvalidCredentialsException(errorMessage);
        }

        String token = jwtUtil.generateToken(doctorEntity.getEmail());

        LoginResponseDto response = doctorMapper.toDto(doctorEntity);
        response.setToken(token);
        log.info("Login successful for user: {}", request.getEmail());

        return ResponseEntity.ok(response);
    }

}
