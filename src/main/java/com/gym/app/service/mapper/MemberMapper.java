package com.gym.app.service.mapper;

import com.gym.app.model.Member;
import com.gym.app.service.dto.MemberDTO;
import com.gym.app.service.dto.MemberPostDTO;
import com.gym.app.service.dto.MembershipDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MembershipTypeMapper.class})
public interface MemberMapper {
    Member toEntity(MemberDTO memberDTO);

    MemberDTO toDto(Member member);

    @Mapping(source = "membershipType.id", target = "membershipTypeId")
    MemberPostDTO toPostDto(Member member);

    @Mapping(source = "membershipTypeId", target = "membershipType.id")
    Member toEntity(MemberPostDTO memberPostDTO);
}
