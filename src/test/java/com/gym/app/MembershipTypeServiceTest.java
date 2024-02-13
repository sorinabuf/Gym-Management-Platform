package com.gym.app;

import com.gym.app.model.MembershipType;
import com.gym.app.repository.MembershipTypeRepository;
import com.gym.app.service.MembershipTypeService;
import com.gym.app.service.MembershipTypeServiceImpl;
import com.gym.app.service.dto.MembershipTypeDTO;
import com.gym.app.service.mapper.MembershipTypeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class MembershipTypeServiceTest {
    @MockBean
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    private MembershipTypeMapper membershipTypeMapper;

    @Autowired
    private MembershipTypeService membershipTypeService;

    @Test
    public void membershipTypeServiceConfigurationTest() {
        assertNotNull(membershipTypeService);
        assertTrue(membershipTypeService instanceof MembershipTypeServiceImpl);
    }

    @Test
    public void addMembershipTypeTest() {
        MembershipTypeDTO inputMembershipTypeDTO = new MembershipTypeDTO();
        inputMembershipTypeDTO.setName("Monthly");
        inputMembershipTypeDTO.setDuration(30);
        inputMembershipTypeDTO.setPrice(100);

        MembershipTypeDTO outputMembershipTypeDTO = new MembershipTypeDTO();
        outputMembershipTypeDTO.setId(1);
        outputMembershipTypeDTO.setName("Monthly");
        outputMembershipTypeDTO.setDuration(30);
        outputMembershipTypeDTO.setPrice(100);

        when(membershipTypeRepository.save(any(MembershipType.class))).thenReturn(
                membershipTypeMapper.toEntity(outputMembershipTypeDTO));

        MembershipTypeDTO addedMembershipType =
                membershipTypeService.addMembershipType(inputMembershipTypeDTO);
        assertEquals(addedMembershipType, outputMembershipTypeDTO);
    }
}
