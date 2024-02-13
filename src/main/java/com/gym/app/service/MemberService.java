package com.gym.app.service;

import com.gym.app.service.dto.MemberDTO;
import com.gym.app.service.dto.MemberPostDTO;
import com.gym.app.service.dto.MembershipDTO;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    MemberPostDTO addMember(MemberPostDTO memberPostDTO);

    Optional<MemberDTO> getMember(int id);

    List<MemberDTO> getAllMembers();

    MemberDTO renewMembership(MembershipDTO membershipDTO);
}
