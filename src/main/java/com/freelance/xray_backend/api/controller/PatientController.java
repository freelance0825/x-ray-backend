package  com.freelance.xray_backend.api.controller;

import com.freelance.xray_backend.application.dto.patient.PatientRequestDto;
import com.freelance.xray_backend.application.dto.patient.PatientResponseDto;
import com.freelance.xray_backend.application.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;


    @PostMapping("/add")
    public ResponseEntity<PatientResponseDto> addPatient(@ModelAttribute PatientRequestDto request) throws IOException {
        PatientResponseDto savePatient = patientService.savePatient(request);
        log.info("Patient created successfully: {}", savePatient);

        return ResponseEntity.ok(savePatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id,
                                                            @ModelAttribute PatientRequestDto request) throws IOException {

        PatientResponseDto updatedPatient = patientService.updateUser(id, request);
        log.info("Patient details updated successfully: {}", updatedPatient);

        return ResponseEntity.ok(updatedPatient);

    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> users = patientService.getAllPatients();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        log.info("Patient with ID: {} has been deleted successfully.", id);

        return ResponseEntity.ok("Patient with ID: " + id + " has been deleted successfully.");
    }

}
