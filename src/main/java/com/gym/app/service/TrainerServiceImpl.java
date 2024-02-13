package com.gym.app.service;

import com.gym.app.service.dto.TrainerDTO;
import com.gym.app.service.mapper.TrainerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.app.repository.TrainerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerMapper trainerMapper;

    @Override
    public TrainerDTO addTrainer(TrainerDTO trainerDTO) {
        return trainerMapper.toDto(trainerRepository.save(trainerMapper.toEntity(trainerDTO)));
    }

    @Override
    public Optional<TrainerDTO> getTrainer(int id) {
        return trainerRepository.findById(id).map(trainerMapper::toDto);
    }

    @Override public List<TrainerDTO> getAllTrainers() {
        return trainerRepository.findAll().stream().map(trainerMapper::toDto)
                .collect(Collectors.toList());
    }
}