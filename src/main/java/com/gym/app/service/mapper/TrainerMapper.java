package com.gym.app.service.mapper;

import com.gym.app.model.Trainer;
import com.gym.app.service.dto.TrainerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TrainerSpecializationMapper.class})
public interface TrainerMapper {
    Trainer toEntity(TrainerDTO trainerDTO);

    TrainerDTO toDto(Trainer trainer);
}
