package com.gym.app.service;

import com.gym.app.repository.GymClassTypeRepository;
import com.gym.app.service.dto.GymClassTypeDTO;
import com.gym.app.service.mapper.GymClassTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymClassTypeServiceImpl implements GymClassTypeService {
    @Autowired
    private GymClassTypeRepository gymClassTypeRepository;

    @Autowired
    private GymClassTypeMapper gymClassTypeMapper;

    @Override
    public GymClassTypeDTO addGymClassType(GymClassTypeDTO gymClassTypeDTO) {
        return gymClassTypeMapper.toDto(
                gymClassTypeRepository.save(gymClassTypeMapper.toEntity(gymClassTypeDTO)));
    }
}
