package com.freelance.fundoscope_backend.application.service;

import com.freelance.fundoscope_backend.application.dto.slide.SlideRequestDto;
import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import com.freelance.fundoscope_backend.domain.entity.DoctorEntity;
import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import com.freelance.fundoscope_backend.domain.mapper.SlideMapper;
import com.freelance.fundoscope_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.fundoscope_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.fundoscope_backend.infrastructure.port.SlidePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        // Retrieve DoctorEntity associated with the CaseRecordEntity
        DoctorEntity doctorEntity = caseRecordEntity.getDoctor();

        // If there's a doctorSignature in the request, save it to the DoctorEntity
        if (request.getDoctorSignature() != null) {
            doctorEntity.setSignature(encodeImageToBase64(request.getDoctorSignature()));
            doctorPersistencePort.save(doctorEntity); // Persist the updated doctor signature
        }

        SlideEntity slideEntity = new SlideEntity();
        slideEntity.setMainImage(encodeImageToBase64(request.getMainImage()));
        slideEntity.setQrCode(encodeImageToBase64(request.getQrCode()));
        slideEntity.setAiInsights(request.getAiInsights());
        slideEntity.setDiagnosis(request.getDiagnosis());
        slideEntity.setMicroscopicDc(request.getMicroscopicDc());
        slideEntity.setClinicalData(request.getClinicalData());
        slideEntity.setCaseRecord(caseRecordEntity);
        slideEntity.setSpecimenType(request.getSpecimenType());
        slideEntity.setCollectionSite(request.getCollectionSite());
        slideEntity.setReportId(request.getReportId());

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
                    .orElseThrow(() -> new IllegalArgumentException("Case not found"));

            existingSlide.setMainImage(encodeImageToBase64(request.getMainImage()));
            existingSlide.setQrCode(encodeImageToBase64(request.getQrCode()));
            existingSlide.setAiInsights(request.getAiInsights());
            existingSlide.setDiagnosis(request.getDiagnosis());
            existingSlide.setClinicalData(request.getClinicalData());
            existingSlide.setCaseRecord(caseRecordEntity);
            existingSlide.setSpecimenType(request.getSpecimenType());
            existingSlide.setCollectionSite(request.getCollectionSite());
            existingSlide.setReportId(request.getReportId());

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

    // Helper method to encode an image file to Base64
    private String encodeImageToBase64(MultipartFile imageFile) throws IOException {
        byte[] imageBytes = imageFile.getBytes();
        return Base64.encodeBase64String(imageBytes);  // Return Base64 string
    }

}
