package com.freelance.xray_backend.domain.repository;

import com.freelance.xray_backend.domain.entity.SlideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<SlideEntity, Long> {

    @Query("SELECT s FROM SlideEntity s WHERE s.caseRecord.id = :caseId")
    List<SlideEntity> findAllByCaseRecordId(@Param("caseId") Long caseId);
}
