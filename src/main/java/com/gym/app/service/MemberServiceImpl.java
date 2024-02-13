package com.gym.app.service;

import com.gym.app.model.Member;
import com.gym.app.model.MembershipType;
import com.gym.app.repository.MemberRepository;
import com.gym.app.repository.MembershipTypeRepository;
import com.gym.app.service.dto.MemberDTO;
import com.gym.app.service.dto.MemberPostDTO;
import com.gym.app.service.dto.MembershipDTO;
import com.gym.app.service.exceptions.BadMemberIdException;
import com.gym.app.service.exceptions.BadMembershipTypeIdException;
import com.gym.app.service.exceptions.MembershipStillValidException;
import com.gym.app.service.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberPostDTO addMember(MemberPostDTO memberPostDTO) {
        Member member = memberMapper.toEntity(memberPostDTO);
        MembershipType membershipType =
                membershipTypeRepository.findById(memberPostDTO.getMembershipTypeId())
                        .orElseThrow(BadMembershipTypeIdException::new);

        int membershipDuration = membershipType.getDuration();
        member.setMembershipExpiryDate(LocalDateTime.now().plusDays(membershipDuration));

        return memberMapper.toPostDto(memberRepository.save(member));
    }

    @Override
    public Optional<MemberDTO> getMember(int id) {
        return memberRepository.findById(id).map(memberMapper::toDto);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(memberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO renewMembership(MembershipDTO membershipDTO) {
        Member member = memberRepository.findById(membershipDTO.getMemberId())
                .orElseThrow(BadMemberIdException::new);
        MembershipType membershipType =
                membershipTypeRepository.findById(membershipDTO.getMembershipTypeId())
                        .orElseThrow(BadMembershipTypeIdException::new);

        if (member.getMembershipExpiryDate().isAfter(LocalDateTime.now())) {
            throw new MembershipStillValidException();
        }

        member.setMembershipType(membershipType);
        member.setMembershipExpiryDate(LocalDateTime.now().plusDays(membershipType.getDuration()));

        return memberMapper.toDto(memberRepository.save(member));
    }
}
