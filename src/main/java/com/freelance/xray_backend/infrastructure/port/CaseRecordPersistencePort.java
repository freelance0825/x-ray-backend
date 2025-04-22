package com.freelance.xray_backend.infrastructure.port;

import com.freelance.xray_backend.domain.entity.CaseRecordEntity;

import java.util.List;
import java.util.Optional;

public interface CaseRecordPersistencePort {

    CaseRecordEntity save(CaseRecordEntity caseRecordEntity);

    List<CaseRecordEntity> findAll();

    Optional<CaseRecordEntity> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

}
