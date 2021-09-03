package com.chun.springboot.converter;

import com.chun.springboot.entity.NewEntity;
import com.chun.springboot.service.dto.NewDTO;

import org.springframework.stereotype.Component;

@Component
public class NewConverter {
    
    public NewEntity toEntity(NewDTO newDTO) {
        NewEntity entity = new NewEntity();
        entity.setTitle(newDTO.getTitle());
        entity.setContent(newDTO.getContent());
        entity.setDescription(newDTO.getDescription());
        entity.setThumbnail(newDTO.getThumbnail());
        return entity;
    }

    public NewDTO toDTO(NewEntity entity) {
        NewDTO dto = new NewDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        dto.setCategoryCode(entity.getCategory().getCode());
        dto.setThumbnail(entity.getThumbnail());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public NewEntity toEntity(NewDTO newDTO, NewEntity entity) {
        entity.setTitle(newDTO.getTitle());
        entity.setContent(newDTO.getContent());
        entity.setDescription(newDTO.getDescription());
        entity.setThumbnail(newDTO.getThumbnail());
        return entity;
    }
}
