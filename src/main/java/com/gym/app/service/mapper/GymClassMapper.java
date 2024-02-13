package com.gym.app.service.mapper;

import com.gym.app.model.GymClass;
import com.gym.app.service.dto.GymClassDTO;
import com.gym.app.service.dto.GymClassPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {GymClassTypeMapper.class, TrainerMapper.class,
        MemberMapper.class})
public interface GymClassMapper {
    @Mapping(source = "gymClassTypeId", target = "gymClassKey.gymClassType.id")
    @Mapping(source = "startTime", target = "gymClassKey.startTime")
    @Mapping(source = "trainerId", target = "trainer.id")
    GymClass toEntity(GymClassPostDTO gymClassPostDTO);

    @Mapping(source = "gymClassKey.gymClassType.id", target = "gymClassTypeId")
    @Mapping(source = "gymClassKey.startTime", target = "startTime")
    @Mapping(source = "trainer.id", target = "trainerId")
    GymClassPostDTO toPostDto(GymClass gymClass);

    @Mapping(source = "gymClassKey.gymClassType", target = "gymClassType")
    @Mapping(source = "gymClassKey.startTime", target = "startTime")
    GymClassDTO toDto(GymClass gymClass);
}
