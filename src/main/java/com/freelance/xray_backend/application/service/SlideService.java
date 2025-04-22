package com.freelance.xray_backend.application.service;

import com.freelance.xray_backend.application.dto.slide.SlideRequestDto;
import com.freelance.xray_backend.application.dto.slide.SlideResponseDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import com.freelance.xray_backend.domain.entity.DoctorEntity;
import com.freelance.xray_backend.domain.entity.SlideEntity;
import com.freelance.xray_backend.domain.mapper.SlideMapper;
import com.freelance.xray_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.xray_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.xray_backend.infrastructure.port.SlidePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlideService {

    private final SlidePersistencePort slidePersistencePort;

    private final CaseRecordPersistencePort caseRecordPersistencePort;

    private final DoctorPersistencePort doctorPersistencePort;

    private final SlideMapper slideMapper;

    public SlideResponseDto saveSlides(SlideRequestDto request) throws IOException {

        // Retrieve CaseRecordEntity by caseRecordId
        CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(request.getCaseRecordId())
                .orElseThrow(() -> new IllegalArgumentException("Case not found"));

        SlideEntity slideEntity = new SlideEntity();
        slideEntity.setMainImage(encodeImageToBase64(request.getMainImage()));
        slideEntity.setQrCode(encodeImageToBase64(request.getQrCode()));
        slideEntity.setAiInsights(request.getAiInsights());
        slideEntity.setMicroscopicDc(request.getMicroscopicDc());
        slideEntity.setDiagnosis(request.getDiagnosis());
        slideEntity.setClinicalData(request.getClinicalData());
        slideEntity.setCaseRecord(caseRecordEntity);
        slideEntity.setSpecimenType(request.getSpecimenType());
        slideEntity.setCollectionSite(request.getCollectionSite());
        slideEntity.setReportId(request.getReportId());
        slideEntity.setDateAndTime(request.getDateAndTime());

        return slideMapper.toDto(slidePersistencePort.save(slideEntity));

    }

    public SlideResponseDto getSlideById(Long id) {
        SlideEntity slideEntity = slidePersistencePort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Slide not found with ID: " + id));

        return slideMapper.toDto(slideEntity);
    }


    public List<SlideResponseDto> getSlidesByCaseId(Long caseId) {
        List<SlideEntity> slides = slidePersistencePort.findAllByCaseRecordId(caseId);
        return slides.isEmpty() ? Collections.emptyList() : slides
                .stream()
                .map(slideMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSlides(Long id) throws IOException {
        log.info("Attempting to delete Slide with ID: {}", id);

        if (!slidePersistencePort.existsById(id)) {
            log.warn("Slide with ID: {} not found. Deletion skipped.", id);
            throw new IllegalArgumentException("Slide not found with ID: " + id);
        }

        try {
            slidePersistencePort.deleteById(id);
            log.info("Slide with ID: {} has been deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting Slide with ID: {}: {}", id, e.getMessage());
            throw new IOException("Error deleting Slide: " + e.getMessage());
        }
    }

    public SlideResponseDto updateSlide(Long id, SlideRequestDto request) throws IOException {
        log.info("Updating slide with ID: {}", id);
        try {
            SlideEntity existingSlide = slidePersistencePort.findById(id).orElse(null);
            if (existingSlide == null) {
                throw new IOException("Slide not found for ID: " + id);
            }

            CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(request.getCaseRecordId())
                    .orElseThrow(() -> new IllegalArgumentException("Case record not found"));

            DoctorEntity doctorEntity = caseRecordEntity.getDoctor();

            // If there's a doctorSignature in the request, save it to the DoctorEntity
            if (request.getDoctorSignature() != null) {
                doctorEntity.setSignature(encodeImageToBase64(request.getDoctorSignature()));
                doctorPersistencePort.save(doctorEntity); // Persist the updated doctor signature
            }

            // Use the helper method to update the fields in existingSlide if they are present in the request
            updateFieldsIfPresent(request, existingSlide);

            // Set the caseRecord (always required)
            existingSlide.setCaseRecord(caseRecordEntity);

            // Save and return the updated Slide
            return slideMapper.toDto(slidePersistencePort.save(existingSlide));

        } catch (Exception e) {
            log.error("Error updating slide: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public List<SlideResponseDto> getAllSlides() {
        log.info("Fetching all slides...");
        List<SlideEntity> slides = slidePersistencePort.findAll();
        return slideMapper.toDtoList(slides);
    }


    private void updateFieldsIfPresent(SlideRequestDto request, SlideEntity existingSlide) {
        try {
            // Update the fields only if the field is present in the request (i.e., non-null)
            if (request.getMainImage() != null) {
                existingSlide.setMainImage(encodeImageToBase64(request.getMainImage()));
            }
            if (request.getQrCode() != null) {
                existingSlide.setQrCode(encodeImageToBase64(request.getQrCode()));
            }
            if (request.getMicroscopicDc() != null) {
                existingSlide.setMicroscopicDc(request.getMicroscopicDc());
            }
            if (request.getDiagnosis() != null) {
                existingSlide.setDiagnosis(request.getDiagnosis());
            }
            if (request.getAiInsights() != null) {
                existingSlide.setAiInsights(request.getAiInsights());
            }
            if (request.getSpecimenType() != null) {
                existingSlide.setSpecimenType(request.getSpecimenType());
            }
            if (request.getCollectionSite() != null) {
                existingSlide.setCollectionSite(request.getCollectionSite());
            }
            if (request.getReportId() != null) {
                existingSlide.setReportId(request.getReportId());
            }
            if (request.getClinicalData() != null) {
                existingSlide.setClinicalData(request.getClinicalData());
            }
            if (request.getDateAndTime() != null) {
                existingSlide.setDateAndTime(request.getDateAndTime());
            }

        } catch (IOException e) {
            log.error("Error encoding image to Base64", e);
            throw new RuntimeException("Error encoding image to Base64", e);
        }
    }


    // Helper method to encode an image file to Base64
    public String encodeImageToBase64(MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            return Base64.getEncoder().encodeToString(imageBytes);  // Convert to Base64
        }
        return null;  // Return null if the file is not provided
    }

}
