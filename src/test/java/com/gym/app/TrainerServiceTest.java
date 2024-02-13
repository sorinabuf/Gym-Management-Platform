package com.gym.app;

import com.gym.app.repository.TrainerRepository;
import com.gym.app.service.TrainerService;
import com.gym.app.service.TrainerServiceImpl;
import com.gym.app.service.mapper.TrainerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gym.app.model.Trainer;
import com.gym.app.service.dto.TrainerDTO;
import com.gym.app.service.dto.TrainerSpecializationDTO;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class TrainerServiceTest {
    @MockBean
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private TrainerService trainerService;

    private TrainerDTO inputTrainerDTO;
    private TrainerDTO outputTrainerDTO;
    private Trainer outputTrainer;

    @BeforeEach
    public void setUp() {
        var specializationDTO1 = new TrainerSpecializationDTO();
        specializationDTO1.setName("Nutrition");

        var specializationDTO2 = new TrainerSpecializationDTO();
        specializationDTO2.setName("Fitness");

        inputTrainerDTO = new TrainerDTO();
        inputTrainerDTO.setFirstName("Anghel");
        inputTrainerDTO.setLastName("Andreescu");
        inputTrainerDTO.setEmail("anghel.a@gmail.com");
        inputTrainerDTO.setPhoneNumber("0723456789");
        inputTrainerDTO.setSpecializations(List.of(specializationDTO1, specializationDTO2));

        outputTrainerDTO = new TrainerDTO();
        outputTrainerDTO.setId(1);
        outputTrainerDTO.setFirstName("Anghel");
        outputTrainerDTO.setLastName("Andreescu");
        outputTrainerDTO.setEmail("anghel.a@gmail.com");
        outputTrainerDTO.setPhoneNumber("0723456789");
        outputTrainerDTO.setSpecializations(List.of(specializationDTO1, specializationDTO2));

        outputTrainer = trainerMapper.toEntity(outputTrainerDTO);
    }

    @Test
    public void trainerServiceConfigurationTest() {
        assertNotNull(trainerService);
        assertTrue(trainerService instanceof TrainerServiceImpl);
    }

    @Test
    public void addTrainerTest() {
        when(trainerRepository.save(any(Trainer.class))).thenReturn(outputTrainer);

        TrainerDTO addedTrainer = trainerService.addTrainer(inputTrainerDTO);
        assertEquals(addedTrainer, outputTrainerDTO);
    }

    @Test
    public void getTrainerShouldReturnTrainerTest() {
        when(trainerRepository.findById(1)).thenReturn(Optional.of(outputTrainer));

        Optional<TrainerDTO> trainer = trainerService.getTrainer(1);
        assertTrue(trainer.isPresent());
        assertEquals(trainer, Optional.ofNullable(outputTrainerDTO));
    }

    @Test
    public void getTrainerShouldNotReturnTrainerTest() {
        when(trainerRepository.findById(1)).thenReturn(Optional.empty());

        Optional<TrainerDTO> trainer = trainerService.getTrainer(1);
        assertFalse(trainer.isPresent());
    }

    @Test
    public void getAllTrainersTest() {
        when(trainerRepository.findAll()).thenReturn(List.of(outputTrainer));

        List<TrainerDTO> trainers = trainerService.getAllTrainers();
        assertEquals(trainers, List.of(outputTrainerDTO));
    }
}
