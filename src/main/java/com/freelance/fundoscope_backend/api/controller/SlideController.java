package com.freelance.fundoscope_backend.api.controller;

import com.freelance.fundoscope_backend.application.dto.slide.SlideRequestDto;
import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.application.service.SlideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/slides")
@RequiredArgsConstructor
@Slf4j
public class SlideController {

    private final SlideService slideService;

    @PostMapping
    public ResponseEntity<SlideResponseDto> createSlide(@RequestBody SlideRequestDto request) throws IOException {
        try {
            // Convert input data into DTO object
            SlideRequestDto formData = new SlideRequestDto();
            formData.setQrCode(request.getQrCode());
            formData.setMainImage(request.getMainImage());
            formData.setAiInsights(request.getAiInsights());
            formData.setDiagnosis(request.getDiagnosis());
            formData.setClinicalData(request.getClinicalData());
            formData.setCaseRecordId(request.getCaseRecordId());
            formData.setSpecimenType(request.getSpecimenType());
            formData.setCollectionSite(request.getCollectionSite());
            formData.setReportId(request.getReportId());

            // Call service method
            SlideResponseDto response = slideService.saveSlides(formData);
            log.info("Slide created successfully: {}", response);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating Slide", e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to retrieve a Case by ID
    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDto> getSlide(@PathVariable Long id) {
        SlideResponseDto response = slideService.getSlideById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/case/{id}")
    public ResponseEntity<List<SlideResponseDto>> getSlidesByCaseId(@PathVariable Long id) {
        List<SlideResponseDto> slideList = slideService.getSlidesByCaseId(id);
        if (slideList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(slideList);
    }

    @GetMapping("/case/{id}/count")
    public ResponseEntity<Long> getSlideCountByCaseId(@PathVariable Long id) {
        List<SlideResponseDto> slideList = slideService.getSlidesByCaseId(id);
        if (slideList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((long) slideList.size());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        try {
            slideService.deleteSlides(id);
            return ResponseEntity.ok("Slide with ID: " + id + " has been deleted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error deleting Slide: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlideResponseDto> updateSlide(@PathVariable Long id, @RequestBody SlideRequestDto request) {
        try {
            SlideResponseDto updatedSlide = slideService.updateSlide(id, request);
            return ResponseEntity.ok(updatedSlide);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<SlideResponseDto>> getAllSlides() {
        List<SlideResponseDto> slides = slideService.getAllSlides();
        return ResponseEntity.ok(slides);
    }

}
