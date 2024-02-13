package com.gym.app.service;

import com.gym.app.model.GymClass;
import com.gym.app.model.Member;
import com.gym.app.repository.GymClassRepository;
import com.gym.app.repository.MemberRepository;
import com.gym.app.service.dto.GymClassDTO;
import com.gym.app.service.dto.GymClassMemberPostDTO;
import com.gym.app.service.dto.GymClassPostDTO;
import com.gym.app.service.exceptions.*;
import com.gym.app.service.mapper.GymClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GymClassServiceImpl implements GymClassService {
    @Autowired
    private GymClassRepository gymClassRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GymClassMapper gymClassMapper;

    @Override
    public GymClassPostDTO addGymClass(GymClassPostDTO gymClassPostDTO) {
        return gymClassMapper.toPostDto(
                gymClassRepository.save(gymClassMapper.toEntity(gymClassPostDTO)));
    }

    @Override
    public Optional<GymClassDTO> getGymClass(int gymClassTypeId, LocalDateTime startTime) {
        return gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassTypeId, startTime).map(gymClassMapper::toDto);
    }

    @Override
    public GymClassDTO signupMember(GymClassMemberPostDTO gymClassMemberPostDTO) {
        Optional<GymClass> gymClass =
                gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                        gymClassMemberPostDTO.getGymClassTypeId(),
                        gymClassMemberPostDTO.getStartTime());

        if (gymClass.isEmpty()) {
            throw new BadGymClassException();
        }

        int gymClassCapacity = gymClass.get().getGymClassKey().getGymClassType().getCapacity();

        if (gymClass.get().getMembers().size() == gymClassCapacity) {
            throw new GymClassFullCapacityException();
        }

        Member member = memberRepository.findById(gymClassMemberPostDTO.getMemberId())
                .orElseThrow(BadMemberIdException::new);

        if (gymClass.get().getMembers().contains(member)) {
            throw new MemberAlreadySignedUpException();
        }

        if (member.getMembershipExpiryDate()
                .isBefore(gymClass.get().getGymClassKey().getStartTime())) {
            throw new MembershipExpiredException();
        }

        gymClass.get().getMembers().add(member);

        return gymClassMapper.toDto(gymClassRepository.save(gymClass.get()));
    }
}
