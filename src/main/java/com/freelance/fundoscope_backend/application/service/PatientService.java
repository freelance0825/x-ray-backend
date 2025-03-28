package com.freelance.fundoscope_backend.application.service;

import com.freelance.fundoscope_backend.application.dto.patient.PatientRequestDto;
import com.freelance.fundoscope_backend.application.dto.patient.PatientResponseDto;
import com.freelance.fundoscope_backend.domain.entity.PatientEntity;
import com.freelance.fundoscope_backend.domain.mapper.PatientMapper;
import com.freelance.fundoscope_backend.infrastructure.port.PatientPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientPersistencePort patientPersistencePort;

    private final PatientMapper patientMapper;

    public PatientResponseDto savePatientWithImage(String name, String address, String gender,
                                                   String email, String state,String status, String type , String age,
                                                   String dob, String phoneNumber, MultipartFile imageFile) throws IOException {

        // Convert image to Base64 string
        String imageBase64 = encodeImageToBase64(imageFile);

        // Create and save patient entity
        PatientEntity patient = new PatientEntity();
        patient.setName(name);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setEmail(email);
        patient.setState(state);
        patient.setStatus(status);
        patient.setType(type);
        patient.setAge(age);
        patient.setDateOfBirth(dob);
        patient.setPhoneNumber(phoneNumber);
        patient.setImageBase64(imageBase64);

        return patientMapper.toDto(patientPersistencePort.save(patient));
    }

    public List<PatientResponseDto> getAllPatients() {
        List<PatientEntity> patientList = patientPersistencePort.findAll();
        return patientMapper.toDtoList(patientList);
    }

    @Transactional
    public void deletePatient(Long id) throws IOException {
        log.info("Deleting patient with ID: {}", id);
        try {
            PatientEntity existingPatient = patientPersistencePort.findById(id).orElse(null);
            if (existingPatient != null) {
                patientPersistencePort.deleteById(id);
                log.info("Patient with ID: {} has been deleted successfully", id);
            } else {
                log.error("Patient with ID: {} not found", id);
                throw new IOException("Patient not found for ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error deleting patient with ID: {}: {}", id, e.getMessage());
            throw new IOException("Error deleting patient: " + e.getMessage());
        }
    }

    public PatientResponseDto updateUser(Long id, PatientRequestDto request) throws IOException {

        log.info("Updating user with ID: {}", id);

        try {

            PatientEntity existingUser = patientPersistencePort.findById(id).orElse(null);
            if (existingUser == null) {
                throw new IOException("User not found for ID: " + id);
            }

            existingUser.setName(request.getName());
            existingUser.setAddress(request.getAddress());
            existingUser.setGender(request.getGender());
            existingUser.setEmail(request.getEmail());
            existingUser.setState(request.getState());
            existingUser.setStatus(request.getStatus());
            existingUser.setType(request.getType());
            existingUser.setAge(request.getAge());
            existingUser.setDateOfBirth(request.getDateOfBirth());
            existingUser.setPhoneNumber(request.getPhoneNumber());
            existingUser.setImageBase64(request.getImageBase64());

            return patientMapper.toDto(patientPersistencePort.save(existingUser));

        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }


    // Helper method to encode an image file to Base64
    private String encodeImageToBase64(MultipartFile imageFile) throws IOException {
        byte[] imageBytes = imageFile.getBytes();
        return Base64.encodeBase64String(imageBytes);  // Return Base64 string
    }

}
