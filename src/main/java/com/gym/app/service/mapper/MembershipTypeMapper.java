package com.gym.app.service.mapper;

import com.gym.app.model.MembershipType;
import com.gym.app.service.dto.MembershipTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembershipTypeMapper {
    MembershipTypeDTO toDto(MembershipType membershipType);

    MembershipType toEntity(MembershipTypeDTO membershipTypeDTO);
}
