package com.freelance.fundoscope_backend.api.controller;

import com.freelance.fundoscope_backend.application.dto.slide.SlideRequestDto;
import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.application.service.SlideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

}
