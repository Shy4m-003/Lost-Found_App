package com.pagasi.lostfound.connection_service.converter;

import com.pagasi.lostfound.connection_service.entities.PostStatus;
import com.pagasi.lostfound.connection_service.entities.RequestPostEntity;
import com.pagasi.lostfound.connection_service.model.RequestPostDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RequestPostConverter {
    public RequestPostEntity dtoToEntity(RequestPostDTO dto) {
        if (dto == null) return null;
        RequestPostEntity entity = new RequestPostEntity();
        entity.setUserId(dto.getUserId());
        entity.setContent(dto.getContent());
        entity.setImages(dto.getImages());
        entity.setCategory(dto.getCategory());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setStatus(PostStatus.ACTIVE);
        entity.setLocationTag(dto.getLocationTag());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        return entity;
    }

    public RequestPostDTO entityToDto(RequestPostEntity entity) {
        if (entity == null) return null;

        RequestPostDTO dto = new RequestPostDTO();
        dto.setUserId(entity.getUserId());
        dto.setContent(entity.getContent());
        dto.setImages(entity.getImages());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLocationTag(entity.getLocationTag());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());

        return dto;
    }
}
