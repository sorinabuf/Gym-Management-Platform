package com.gym.app.service;

import com.gym.app.service.dto.TrainerDTO;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    TrainerDTO addTrainer(TrainerDTO trainerDTO);

    Optional<TrainerDTO> getTrainer(int id);

    List<TrainerDTO> getAllTrainers();
}
