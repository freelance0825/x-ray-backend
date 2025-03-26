package com.freelance.fundoscope_backend.application.service;

import com.freelance.fundoscope_backend.application.dto.slide.SlideRequestDto;
import com.freelance.fundoscope_backend.application.dto.slide.SlideResponseDto;
import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import com.freelance.fundoscope_backend.domain.mapper.SlideMapper;
import com.freelance.fundoscope_backend.infrastructure.port.CaseRecordPersistencePort;
import com.freelance.fundoscope_backend.infrastructure.port.SlidePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlideService {

    private final SlidePersistencePort slidePersistencePort;

    private final CaseRecordPersistencePort caseRecordPersistencePort;

    private final SlideMapper slideMapper;

    public SlideResponseDto saveSlides(SlideRequestDto request) throws IOException {

        CaseRecordEntity caseRecordEntity = caseRecordPersistencePort.findById(request.getCaseRecordId())
                .orElseThrow(() -> new IllegalArgumentException("Case not found"));

        SlideEntity slideEntity = new SlideEntity();
        slideEntity.setMainImage(request.getMainImage());
        slideEntity.setQrCode(request.getQrCode());
        slideEntity.setAiInsights(request.getAiInsights());
        slideEntity.setDiagnosis(request.getDiagnosis());
        slideEntity.setClinicalData(request.getClinicalData());
        slideEntity.setCaseRecord(caseRecordEntity);
        slideEntity.setSpecimenType(request.getSpecimenType());
        slideEntity.setCollectionSite(request.getCollectionSite());
        slideEntity.setReportId(request.getReportId());

        return slideMapper.toDto(slidePersistencePort.save(slideEntity));

    }


}
