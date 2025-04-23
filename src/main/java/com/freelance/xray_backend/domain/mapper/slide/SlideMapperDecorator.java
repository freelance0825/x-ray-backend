package com.freelance.xray_backend.domain.mapper.slide;

import com.freelance.xray_backend.application.dto.slide.SlideRequestDto;
import com.freelance.xray_backend.domain.entity.SlideEntity;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public abstract class SlideMapperDecorator implements SlideMapper {

    @Autowired
    @Qualifier("delegate")
    private SlideMapper delegate;

    @Override
    public SlideEntity toEntity(SlideRequestDto requestDto) {
        SlideEntity entity = delegate.toEntity(requestDto);

        // Handle image to Base64 encoding here
        entity.setMainImage(encodeImageToBase64(requestDto.getMainImage()));
        entity.setQrCode(encodeImageToBase64(requestDto.getQrCode()));

        return entity;
    }

    @Override
    public void updateSlidesFromDto(SlideRequestDto dto, @MappingTarget SlideEntity entity) {
        delegate.updateSlidesFromDto(dto, entity);

        if (dto.getMainImage() != null && !dto.getMainImage().isEmpty()) {
            entity.setMainImage(encodeImageToBase64(dto.getMainImage()));
        }

        if (dto.getQrCode() != null && !dto.getQrCode().isEmpty()) {
            entity.setQrCode(encodeImageToBase64(dto.getQrCode()));
        }
    }

    private String encodeImageToBase64(MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                byte[] imageBytes = imageFile.getBytes();
                return Base64.getEncoder().encodeToString(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to encode image to Base64", e);
            }
        }
        return null;
    }

}