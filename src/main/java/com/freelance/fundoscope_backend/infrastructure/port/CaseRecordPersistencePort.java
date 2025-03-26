package com.freelance.fundoscope_backend.infrastructure.port;

import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;

import java.util.List;

public interface CaseRecordPersistencePort {

    CaseRecordEntity save(CaseRecordEntity caseRecordEntity);

    List<CaseRecordEntity> findAll();

}
