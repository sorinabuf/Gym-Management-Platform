package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class TrainerDTO {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private List<TrainerSpecializationDTO> specializations;
}
