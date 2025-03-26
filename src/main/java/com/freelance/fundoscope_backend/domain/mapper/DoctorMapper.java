package com.freelance.fundoscope_backend.domain.mapper;

import com.freelance.fundoscope_backend.application.dto.caserecord.DoctorDto;
import com.freelance.fundoscope_backend.application.dto.doctor.DoctorResponseDto;
import com.freelance.fundoscope_backend.application.dto.doctor.LoginResponseDto;
import com.freelance.fundoscope_backend.domain.entity.DoctorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponseDto mapToDto(DoctorEntity doctorEntity);

    LoginResponseDto toDto (DoctorEntity doctorEntity);

    DoctorDto toDoctorDto(DoctorEntity doctorEntity);

}
