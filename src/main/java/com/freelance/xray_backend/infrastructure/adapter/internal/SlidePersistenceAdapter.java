package com.freelance.xray_backend.infrastructure.adapter.internal;

import com.freelance.xray_backend.domain.entity.SlideEntity;
import com.freelance.xray_backend.domain.repository.SlideRepository;
import com.freelance.xray_backend.infrastructure.port.SlidePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlidePersistenceAdapter implements SlidePersistencePort {

    private final SlideRepository slideRepository;

    @Override
    public SlideEntity save(SlideEntity slideEntity) {
        return slideRepository.save(slideEntity);
    }

    @Override
    public Optional<SlideEntity> findById(Long id) {
        return slideRepository.findById(id);
    }

    @Override
    public List<SlideEntity> findAllByCaseRecordId(Long caseId) {
        return slideRepository.findAllByCaseRecordId(caseId);
    }

    @Override
    public boolean existsById(Long id) {
        return slideRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        slideRepository.deleteById(id);
    }

    @Override
    public List<SlideEntity> findAll() {
        return slideRepository.findAll();
    }
}
