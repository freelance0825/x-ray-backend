package com.freelance.fundoscope_backend.infrastructure.adapter.internal;

import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import com.freelance.fundoscope_backend.domain.repository.SlideRepository;
import com.freelance.fundoscope_backend.infrastructure.port.SlidePersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlidePersistenceAdapter implements SlidePersistencePort {

    private final SlideRepository slideRepository;

    @Override
    public SlideEntity save(SlideEntity slideEntity) {
        return slideRepository.save(slideEntity);
    }
}
