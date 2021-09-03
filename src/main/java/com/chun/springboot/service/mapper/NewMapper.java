package com.chun.springboot.service.mapper;

import com.chun.springboot.entity.NewEntity;
import com.chun.springboot.service.dto.NewDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class NewMapper {

    
    @Mapping(target = "category.code", source = "categoryCode")
    public abstract NewEntity toNewEntity(NewDTO dto);
    @Mapping(target = "categoryCode", source = "category.code")
    public abstract NewDTO toNewDTO(NewEntity entity);
}
