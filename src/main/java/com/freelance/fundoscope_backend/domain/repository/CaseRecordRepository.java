package com.freelance.fundoscope_backend.domain.repository;

import com.freelance.fundoscope_backend.application.dto.caserecord.CaseRecordRequestDto;
import com.freelance.fundoscope_backend.domain.entity.CaseRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRecordRepository extends JpaRepository<CaseRecordEntity, Long> {

}
