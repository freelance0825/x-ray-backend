package com.freelance.xray_backend.infrastructure.adapter.internal;

import com.freelance.xray_backend.domain.entity.CaseRecordEntity;
import com.freelance.xray_backend.domain.repository.CaseRecordRepository;
import com.freelance.xray_backend.infrastructure.port.CaseRecordPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


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

    @Override
    public Optional<CaseRecordEntity> findById(Long id) {
        return caseRecordRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return caseRecordRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        caseRecordRepository.deleteById(id);
    }
}
