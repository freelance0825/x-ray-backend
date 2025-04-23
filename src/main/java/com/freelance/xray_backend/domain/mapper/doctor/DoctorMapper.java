package com.freelance.xray_backend.domain.mapper.doctor;

import com.freelance.xray_backend.application.dto.caserecord.DoctorDto;
import com.freelance.xray_backend.application.dto.doctor.DoctorRequestDto;
import com.freelance.xray_backend.application.dto.doctor.DoctorResponseDto;
import com.freelance.xray_backend.application.dto.doctor.LoginResponseDto;
import com.freelance.xray_backend.domain.entity.DoctorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponseDto toDto(DoctorEntity doctorEntity);

    LoginResponseDto toLoginDto (DoctorEntity doctorEntity);

    DoctorEntity toEntity(DoctorRequestDto doctorRequestDto);

}
