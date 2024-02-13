package com.gym.app.service;

import com.gym.app.repository.MembershipTypeRepository;
import com.gym.app.service.dto.MembershipTypeDTO;
import com.gym.app.service.mapper.MembershipTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipTypeServiceImpl implements MembershipTypeService {
    @Autowired
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    private MembershipTypeMapper membershipTypeMapper;

    @Override
    public MembershipTypeDTO addMembershipType(MembershipTypeDTO membershipTypeDTO) {
        return membershipTypeMapper.toDto(
                membershipTypeRepository.save(membershipTypeMapper.toEntity(membershipTypeDTO)));
    }
}
