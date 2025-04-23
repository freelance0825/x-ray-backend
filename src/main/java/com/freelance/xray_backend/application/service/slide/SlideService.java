package com.freelance.xray_backend.application.service.slide;

import com.freelance.xray_backend.application.dto.slide.SlideRequestDto;
import com.freelance.xray_backend.application.dto.slide.SlideResponseDto;
import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import com.freelance.xray_backend.domain.entity.DoctorEntity;
import com.freelance.xray_backend.domain.entity.SlideEntity;
import com.freelance.xray_backend.domain.mapper.slide.SlideMapper;
import com.freelance.xray_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.xray_backend.infrastructure.port.DoctorPersistencePort;
import com.freelance.xray_backend.infrastructure.port.SlidePersistencePort;
import com.freelance.xray_backend.shared.exception.CaseRecordNotFoundException;
import com.freelance.xray_backend.shared.exception.SlideRecordNotFoundException;
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
        CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(request.getCaseRecordId())
                .orElseThrow(() -> new CaseRecordNotFoundException("Case record not found"));

        SlideEntity slideEntity = slideMapper.toEntity(request);
        slideEntity.setCaseRecord(caseRecordEntity);

        return slideMapper.toDto(slidePersistencePort.save(slideEntity));
    }

    public SlideResponseDto updateSlide(Long id, SlideRequestDto request) throws IOException {
        log.info("Updating slide with ID: {}", id);

        try {
            SlideEntity existingSlide = slidePersistencePort.findById(id)
                    .orElseThrow(() -> new SlideRecordNotFoundException("Slide record not found"));

            CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(request.getCaseRecordId())
                    .orElseThrow(() -> new CaseRecordNotFoundException("Case record not found"));

            DoctorEntity doctorEntity = caseRecordEntity.getDoctor();

            // Handle doctor signature update
            if (request.getDoctorSignature() != null) {
                doctorEntity.setSignature(encodeImageToBase64(request.getDoctorSignature()));
                doctorPersistencePort.save(doctorEntity);
            }

            // Map update fields (decorator handles images too)
            slideMapper.updateSlidesFromDto(request, existingSlide);
            existingSlide.setCaseRecord(caseRecordEntity);

            return slideMapper.toDto(slidePersistencePort.save(existingSlide));

        } catch (Exception e) {
            log.error("Error updating slide: {}", e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public SlideResponseDto getSlideById(Long id) {
        SlideEntity slideEntity = slidePersistencePort.findById(id)
                .orElseThrow(() -> new SlideRecordNotFoundException("Slide not found with ID: " + id));

        return slideMapper.toDto(slideEntity);
    }

    public List<SlideResponseDto> getSlidesByCaseId(Long caseId) {
        List<SlideEntity> slides = slidePersistencePort.findAllByCaseRecordId(caseId);

        return slideMapper.toDtoList(slides);
    }

    public List<SlideResponseDto> getAllSlides() {
        List<SlideEntity> slides = slidePersistencePort.findAll();

        return slideMapper.toDtoList(slides);
    }

    @Transactional
    public void deleteSlideRecord(Long id) {
        log.info("Attempting to delete Slide record with ID: {}", id);

        try {
            SlideEntity existingSlide = slidePersistencePort.findById(id)
                    .orElseThrow(() -> new SlideRecordNotFoundException("Slide record not found for ID: " + id));

            slidePersistencePort.deleteById(id);
            log.info("Slide record with ID: {} has been deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting slide record with ID: {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting slide recor: " + e.getMessage());
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