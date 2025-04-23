package com.freelance.xray_backend.api.controller;

import com.freelance.xray_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.xray_backend.application.dto.caserecord.CaseRecordsResponseDto;
import com.freelance.xray_backend.application.service.caseRecord.CaseRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/case")
@RequiredArgsConstructor
@Slf4j
public class CaseRecordController {

    private final CaseRecordService caseRecordService;

    @PostMapping
    public ResponseEntity<CaseRecordsResponseDto> createCaseRecord(@RequestBody CaseRecordRequestDto request) {
        log.info("Received case record request {}", request);

        CaseRecordsResponseDto response = caseRecordService.saveCaseRecord(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaseRecordsResponseDto> updateCaseRecord(@PathVariable Long id,
                                                                   @RequestBody CaseRecordRequestDto request) {

        CaseRecordsResponseDto updatedCaseRecord = caseRecordService.updateCaseRecord(id, request);
        log.info("Case record details updated successfully: {}", updatedCaseRecord);

        return ResponseEntity.ok(updatedCaseRecord);

    }

    // Endpoint to retrieve a Case by ID
    @GetMapping("/{id}")
    public ResponseEntity<CaseRecordsResponseDto> getCaseRecordById(@PathVariable Long id) {
        CaseRecordsResponseDto response = caseRecordService.getCaseRecordById(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCaseRecord(@PathVariable Long id) {
        caseRecordService.deleteCaseRecord(id);
        log.info("Case record with ID: {} has been deleted successfully.", id);

        return ResponseEntity.ok("Case record with ID: " + id + " has been deleted successfully.");
    }

    @GetMapping("/list")
    public ResponseEntity<List<CaseRecordsResponseDto>> getAllCaseRecords() {
        List<CaseRecordsResponseDto> caseRecords = caseRecordService.getAllCaseRecords();
        return ResponseEntity.ok(caseRecords);

    }
}
