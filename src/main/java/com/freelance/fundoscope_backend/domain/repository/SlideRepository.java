package com.freelance.fundoscope_backend.domain.repository;

import com.freelance.fundoscope_backend.domain.entity.SlideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<SlideEntity, Long> {
}
