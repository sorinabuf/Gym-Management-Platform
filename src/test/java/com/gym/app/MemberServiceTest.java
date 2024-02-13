package com.gym.app;

import com.gym.app.model.Member;
import com.gym.app.model.MembershipType;
import com.gym.app.repository.MemberRepository;
import com.gym.app.repository.MembershipTypeRepository;
import com.gym.app.service.MemberService;
import com.gym.app.service.MemberServiceImpl;
import com.gym.app.service.dto.MemberDTO;
import com.gym.app.service.dto.MemberPostDTO;
import com.gym.app.service.dto.MembershipDTO;
import com.gym.app.service.exceptions.BadMemberIdException;
import com.gym.app.service.exceptions.BadMembershipTypeIdException;
import com.gym.app.service.exceptions.MembershipStillValidException;
import com.gym.app.service.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {
    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    private MemberPostDTO inputMemberPostDTO;
    private MembershipType membershipType;
    private Member member;
    private MemberDTO memberDTO;
    private MembershipDTO membershipDTO;

    @BeforeEach
    public void setUp() {
        inputMemberPostDTO = new MemberPostDTO();
        inputMemberPostDTO.setFirstName("Andrei");
        inputMemberPostDTO.setLastName("Popescu");
        inputMemberPostDTO.setEmail("andrei.popescu@yahoo.com");
        inputMemberPostDTO.setPhoneNumber("0723456789");
        inputMemberPostDTO.setHomeAddress("Str. Mieilor, nr. 1");
        inputMemberPostDTO.setMembershipTypeId(1);

        membershipType = new MembershipType();
        membershipType.setId(1);
        membershipType.setName("Monthly");
        membershipType.setDuration(30);
        membershipType.setPrice(100);

        member = new Member();
        member.setId(1);
        member.setFirstName("Andrei");
        member.setLastName("Popescu");
        member.setEmail("andrei.popescu@yahoo.com");
        member.setPhoneNumber("0723456789");
        member.setHomeAddress("Str. Mieilor, nr. 1");
        member.setMembershipType(membershipType);
        member.setMembershipExpiryDate(LocalDateTime.now().plusDays(30));

        memberDTO = memberMapper.toDto(member);

        membershipDTO = new MembershipDTO();
        membershipDTO.setMemberId(1);
        membershipDTO.setMembershipTypeId(2);
    }

    @Test
    public void memberServiceConfigurationTest() {
        assertNotNull(memberService);
        assertTrue(memberService instanceof MemberServiceImpl);
    }

    @Test
    public void addMemberTest() {
        MemberPostDTO outputMemberPostDTO = new MemberPostDTO();
        outputMemberPostDTO.setId(1);
        outputMemberPostDTO.setFirstName("Andrei");
        outputMemberPostDTO.setLastName("Popescu");
        outputMemberPostDTO.setEmail("andrei.popescu@yahoo.com");
        outputMemberPostDTO.setPhoneNumber("0723456789");
        outputMemberPostDTO.setHomeAddress("Str. Mieilor, nr. 1");
        outputMemberPostDTO.setMembershipTypeId(1);

        when(memberRepository.save(any(Member.class))).thenReturn(
                memberMapper.toEntity(outputMemberPostDTO));
        when(membershipTypeRepository.findById(1)).thenReturn(Optional.of(membershipType));

        MemberPostDTO addedMember = memberService.addMember(inputMemberPostDTO);
        assertEquals(addedMember, outputMemberPostDTO);
    }

    @Test
    public void addMemberThrowsBadMembershipTypeIdExceptionTest() {
        when(membershipTypeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadMembershipTypeIdException.class,
                () -> memberService.addMember(inputMemberPostDTO));
    }

    @Test
    public void getMemberShouldReturnMemberTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));

        Optional<MemberDTO> member = memberService.getMember(1);
        assertTrue(member.isPresent());
        assertEquals(member, Optional.of(memberDTO));
    }

    @Test
    public void getMemberShouldNotReturnMemberTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.empty());

        Optional<MemberDTO> member = memberService.getMember(1);
        assertFalse(member.isPresent());
    }

    @Test
    public void getAllMembersTest() {
        when(memberRepository.findAll()).thenReturn(List.of(member));

        List<MemberDTO> members = memberService.getAllMembers();
        assertEquals(members, List.of(memberDTO));
    }

    @Test
    public void renewMembershipThrowsBadMemberIdExceptionTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadMemberIdException.class,
                () -> memberService.renewMembership(membershipDTO));
    }

    @Test
    public void renewMembershipThrowsBadMembershipTypeIdExceptionTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(membershipTypeRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(BadMembershipTypeIdException.class,
                () -> memberService.renewMembership(membershipDTO));
    }

    @Test
    public void renewMembershipThrowsMembershipStillValidExceptionTest() {
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(membershipTypeRepository.findById(2)).thenReturn(Optional.of(new MembershipType()));

        assertThrows(MembershipStillValidException.class,
                () -> memberService.renewMembership(membershipDTO));
    }

    @Test
    public void renewMembershipAppliesNewMembershipTest() {
        MembershipType newMembershipType = new MembershipType();
        newMembershipType.setId(2);
        newMembershipType.setName("Yearly");
        newMembershipType.setDuration(365);
        newMembershipType.setPrice(1000);

        member.setMembershipExpiryDate(LocalDateTime.now().minusDays(1));

        Member renewedMember = new Member();
        renewedMember.setId(1);
        renewedMember.setFirstName("Andrei");
        renewedMember.setLastName("Popescu");
        renewedMember.setEmail("andrei.popescu@yahoo.com");
        renewedMember.setPhoneNumber("0723456789");
        renewedMember.setHomeAddress("Str. Mieilor, nr. 1");
        renewedMember.setMembershipType(newMembershipType);
        renewedMember.setMembershipExpiryDate(LocalDateTime.now().plusDays(365));

        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(membershipTypeRepository.findById(2)).thenReturn(Optional.of(newMembershipType));
        when(memberRepository.save(any(Member.class))).thenReturn(renewedMember);

        assertEquals(memberService.renewMembership(membershipDTO),
                memberMapper.toDto(renewedMember));
    }
}
