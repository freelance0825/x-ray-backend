package com.freelance.xray_backend.api.controller;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordListResponseDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.application.service.CaseRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/case")
@RequiredArgsConstructor
@Slf4j
public class CaseRecordController {

    private final CaseRecordService caseRecordService;

    @PostMapping
    public ResponseEntity<CaseRecordListResponseDto> createCase(@RequestBody CaseRecordRequestDto request) throws IOException {
        try {

            // Convert input data into DTO object
            CaseRecordRequestDto formData = new CaseRecordRequestDto();
            formData.setDoctorId(request.getDoctorId());
            formData.setPatientId(request.getPatientId());
            formData.setTodo(request.getTodo());
            formData.setDate(request.getDate());
            formData.setTime(request.getTime());
            formData.setYear(request.getYear());
            formData.setStatus(request.getStatus());
            formData.setType(request.getType());

            // Call service method
            CaseRecordListResponseDto response = caseRecordService.saveCase(formData);
            log.info("Case created successfully: {}", response);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating Case", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Endpoint to retrieve a Case by ID
    @GetMapping("/{id}")
    public ResponseEntity<CaseRecordListResponseDto> getCase(@PathVariable Long id) {
        CaseRecordListResponseDto response = caseRecordService.getCaseById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            caseRecordService.deleteCase(id);
            return ResponseEntity.ok("Case with ID: " + id + " has been deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting Case: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaseRecordListResponseDto> updateCase(@PathVariable Long id,
                                                                @RequestBody CaseRecordRequestDto request) {
        try {
            CaseRecordListResponseDto updatedCaseRecord = caseRecordService.updateCase(id, request);
            return ResponseEntity.ok(updatedCaseRecord);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint to retrieve all Cases
    @GetMapping("/list")
    public ResponseEntity<List<CaseRecordListResponseDto>> getAllCases() {
        List<CaseRecordListResponseDto> caseRecords = caseRecordService.getAllCases();
        return ResponseEntity.ok(caseRecords);

    }

}
