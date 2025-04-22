package com.freelance.xray_backend.domain.repository;

import com.freelance.xray_backend.domain.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    Optional<DoctorEntity> findByEmail(String email);

    boolean existsByEmail(String email);

}
