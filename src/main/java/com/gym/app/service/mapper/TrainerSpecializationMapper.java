package com.gym.app.service.mapper;

import com.gym.app.model.TrainerSpecialization;
import com.gym.app.service.dto.TrainerSpecializationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerSpecializationMapper {
    TrainerSpecialization toEntity(TrainerSpecializationDTO trainerSpecializationDTO);

    TrainerSpecializationDTO toDto(TrainerSpecialization trainerSpecialization);
}
