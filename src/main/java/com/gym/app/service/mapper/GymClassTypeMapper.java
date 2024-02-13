package com.gym.app.service.mapper;

import com.gym.app.model.GymClassType;
import com.gym.app.service.dto.GymClassTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GymClassTypeMapper {
    GymClassType toEntity(GymClassTypeDTO gymClassTypeDTO);

    GymClassTypeDTO toDto(GymClassType gymClassType);
}
