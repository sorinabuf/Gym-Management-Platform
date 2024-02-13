package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor
public class GymClassDTO {
    private GymClassTypeDTO gymClassType;

    private LocalDateTime startTime;

    private TrainerDTO trainer;

    private List<MemberDTO> members;
}
