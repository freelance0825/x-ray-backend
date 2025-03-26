package com.freelance.fundoscope_backend.api.controller;

import com.freelance.fundoscope_backend.application.dto.patient.PatientResponseDto;
import com.freelance.fundoscope_backend.application.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;


    @PostMapping("/patients/add")
    public ResponseEntity<PatientResponseDto> addPatient(@RequestParam("name") String name,
                                                         @RequestParam("address") String address,
                                                         @RequestParam("gender") String gender,
                                                         @RequestParam("email") String email,
                                                         @RequestParam("state") String state,
                                                         @RequestParam("status") String status,
                                                         @RequestParam("type") String type,
                                                         @RequestParam("age") String age,
                                                         @RequestParam("dob") String dob,
                                                         @RequestParam("phoneNumber") String phoneNumber,
                                                         @RequestParam("image") MultipartFile image) throws IOException {


        PatientResponseDto savedUser = patientService.savePatientWithImage(name, address, gender, email,
                state, status, type, age, dob, phoneNumber, image);
        return ResponseEntity.ok(savedUser);
    }


    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id,
                                                            @RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "address", required = false) String address,
                                                            @RequestParam(value = "gender", required = false) String gender,
                                                            @RequestParam(value = "email", required = false) String email,
                                                            @RequestParam(value = "state", required = false) String state,
                                                            @RequestParam(value = "status", required = false) String status,
                                                            @RequestParam(value = "type", required = false) String type,
                                                            @RequestParam(value = "age", required = false) String age,
                                                            @RequestParam(value = "dob", required = false) String dob,
                                                            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                                            @RequestParam("image") MultipartFile image) throws IOException {


        try {
            PatientResponseDto updatedUser = patientService.updateUser(id, name, address, gender, email, state, status, type, age, dob, phoneNumber, image);
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> users = patientService.getAllPatients();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok("Patient with ID: " + id + " has been deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
        }
    }


}
