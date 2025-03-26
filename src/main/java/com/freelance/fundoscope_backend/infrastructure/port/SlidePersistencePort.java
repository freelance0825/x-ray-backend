package com.freelance.fundoscope_backend.infrastructure.port;

import com.freelance.fundoscope_backend.domain.entity.SlideEntity;

public interface SlidePersistencePort {

    SlideEntity save(SlideEntity slideEntity);
}
