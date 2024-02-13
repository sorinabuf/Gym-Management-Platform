package com.gym.app.service;

import com.gym.app.service.dto.GymClassDTO;
import com.gym.app.service.dto.GymClassMemberPostDTO;
import com.gym.app.service.dto.GymClassPostDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GymClassService {
    GymClassPostDTO addGymClass(GymClassPostDTO gymClassPostDTO);

    Optional<GymClassDTO> getGymClass(int gymClassTypeId, LocalDateTime startTime);

    GymClassDTO signupMember(GymClassMemberPostDTO gymClassMemberPostDTO);
}
