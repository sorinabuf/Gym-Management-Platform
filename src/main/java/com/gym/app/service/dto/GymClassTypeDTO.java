package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class GymClassTypeDTO {
    private int id;

    private String name;

    private int duration;

    private int capacity;

    private String intensityLevel;
}
