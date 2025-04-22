package com.freelance.xray_backend.infrastructure.port;

import com.freelance.xray_backend.domain.entity.SlideEntity;

import java.util.List;
import java.util.Optional;

public interface SlidePersistencePort {

    SlideEntity save(SlideEntity slideEntity);

    Optional<SlideEntity> findById(Long id);

    List<SlideEntity> findAllByCaseRecordId(Long caseId);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<SlideEntity> findAll();
}
