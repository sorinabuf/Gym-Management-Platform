package com.gym.app;

import com.gym.app.model.GymClassType;
import com.gym.app.repository.GymClassTypeRepository;
import com.gym.app.service.GymClassTypeService;
import com.gym.app.service.GymClassTypeServiceImpl;
import com.gym.app.service.dto.GymClassTypeDTO;
import com.gym.app.service.mapper.GymClassTypeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GymClassTypeServiceTest {
    @MockBean
    private GymClassTypeRepository gymClassTypeRepository;

    @Autowired
    private GymClassTypeMapper gymClassTypeMapper;

    @Autowired
    private GymClassTypeService gymClassTypeService;

    @Test
    public void gymClassTypeServiceConfigurationTest() {
        assertNotNull(gymClassTypeService);
        assertTrue(gymClassTypeService instanceof GymClassTypeServiceImpl);
    }

    @Test
    public void addGymClassTypeTest() {
        GymClassTypeDTO inputGymClassTypeDTO = new GymClassTypeDTO();
        inputGymClassTypeDTO.setName("Tae Bo");
        inputGymClassTypeDTO.setDuration(60);
        inputGymClassTypeDTO.setCapacity(20);
        inputGymClassTypeDTO.setIntensityLevel("BEGINNER");

        GymClassTypeDTO outputGymClassTypeDTO = new GymClassTypeDTO();
        outputGymClassTypeDTO.setId(1);
        outputGymClassTypeDTO.setName("Tae Bo");
        outputGymClassTypeDTO.setDuration(60);
        outputGymClassTypeDTO.setCapacity(20);
        outputGymClassTypeDTO.setIntensityLevel("BEGINNER");

        when(gymClassTypeRepository.save(any(GymClassType.class))).thenReturn(
                gymClassTypeMapper.toEntity(outputGymClassTypeDTO));

        GymClassTypeDTO addedGymClassType =
                gymClassTypeService.addGymClassType(inputGymClassTypeDTO);
        assertEquals(addedGymClassType, outputGymClassTypeDTO);
    }
}
