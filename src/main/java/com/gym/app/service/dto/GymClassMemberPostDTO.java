package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class GymClassMemberPostDTO {
    private int gymClassTypeId;

    private LocalDateTime startTime;

    private int memberId;
}
