package com.freelance.fundoscope_backend.infrastructure.adapter.internal;

import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import com.freelance.fundoscope_backend.domain.repository.CaseRecordRepository;
import com.freelance.fundoscope_backend.infrastructure.port.CaseRecordPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class CaseRecordPersistenceAdapter implements CaseRecordPersistencePort {

    private final CaseRecordRepository caseRecordRepository;

    @Override
    public CaseRecordEntity save(CaseRecordEntity caseRecordEntity) {
        return caseRecordRepository.save(caseRecordEntity);
    }

    @Override
    public List<CaseRecordEntity> findAll() {
        return caseRecordRepository.findAll();
    }
}
