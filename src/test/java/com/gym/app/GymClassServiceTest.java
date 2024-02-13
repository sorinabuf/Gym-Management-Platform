package com.gym.app;

import com.gym.app.model.*;
import com.gym.app.repository.GymClassRepository;
import com.gym.app.repository.MemberRepository;
import com.gym.app.service.GymClassService;
import com.gym.app.service.GymClassServiceImpl;
import com.gym.app.service.dto.*;
import com.gym.app.service.exceptions.*;
import com.gym.app.service.mapper.GymClassMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class GymClassServiceTest {
    @MockBean
    private GymClassRepository gymClassRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private GymClassMapper gymClassMapper;

    @Autowired
    private GymClassService gymClassService;

    private GymClassMemberPostDTO gymClassMemberPostDTO;
    private GymClass gymClass;
    private GymClass newGymClass;

    @BeforeEach
    public void setUp() {
        gymClassMemberPostDTO = new GymClassMemberPostDTO();
        gymClassMemberPostDTO.setGymClassTypeId(1);
        gymClassMemberPostDTO.setStartTime(LocalDateTime.now());
        gymClassMemberPostDTO.setMemberId(1);

        var gymClassType = new GymClassType();
        gymClassType.setId(1);
        gymClassType.setName("Tae Bo");
        gymClassType.setDuration(60);
        gymClassType.setCapacity(1);
        gymClassType.setIntensityLevel("BEGINNER");

        var gymClassKey = new GymClassKey();
        gymClassKey.setGymClassType(gymClassType);
        gymClassKey.setStartTime(LocalDateTime.now());

        var specialization1 = new TrainerSpecialization();
        specialization1.setName("Nutrition");

        var trainer = new Trainer();
        trainer.setId(1);
        trainer.setFirstName("Anghel");
        trainer.setLastName("Andreescu");
        trainer.setEmail("anghel.a@gmail.com");
        trainer.setPhoneNumber("0723456789");
        trainer.setSpecializations(List.of(specialization1));

        gymClass = new GymClass();
        gymClass.setGymClassKey(gymClassKey);
        gymClass.setTrainer(trainer);
        gymClass.setMembers(new ArrayList<>());

        newGymClass = new GymClass();
        newGymClass.setGymClassKey(gymClassKey);
        newGymClass.setTrainer(trainer);
        newGymClass.setMembers(new ArrayList<>());
    }

    @Test
    public void gymClassTypeServiceConfigurationTest() {
        assertNotNull(gymClassService);
        assertTrue(gymClassService instanceof GymClassServiceImpl);
    }

    @Test
    public void addGymClassTest() {
        GymClassPostDTO gymClassPostDTO = new GymClassPostDTO();
        gymClassPostDTO.setGymClassTypeId(1);
        gymClassPostDTO.setStartTime(LocalDateTime.now());
        gymClassPostDTO.setTrainerId(1);

        when(gymClassRepository.save(any(GymClass.class))).thenReturn(
                gymClassMapper.toEntity(gymClassPostDTO));

        GymClassPostDTO addedGymClass = gymClassService.addGymClass(gymClassPostDTO);
        assertEquals(addedGymClass, gymClassPostDTO);
    }

    @Test
    public void getGymClassShouldReturnGymClassTest() {
        GymClassDTO gymClassDTO = gymClassMapper.toDto(gymClass);
        int gymClassTypeId = gymClass.getGymClassKey().getGymClassType().getId();
        LocalDateTime gymClassStartTime = gymClass.getGymClassKey().getStartTime();

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassTypeId, gymClassStartTime)).thenReturn(Optional.of(gymClass));

        Optional<GymClassDTO> gymClassResult =
                gymClassService.getGymClass(gymClassTypeId, gymClassStartTime);
        assertTrue(gymClassResult.isPresent());
        assertEquals(gymClassResult, Optional.of(gymClassDTO));
    }

    @Test
    public void getGymClassShouldReturnEmptyGymClassTest() {
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(1,
                LocalDateTime.now())).thenReturn(Optional.empty());

        Optional<GymClassDTO> gymClassResult =
                gymClassService.getGymClass(1, LocalDateTime.now());
        assertFalse(gymClassResult.isPresent());
    }

    @Test
    public void signUpMemberThrowsBadGymClassExceptionTest() {
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.empty());

        assertThrows(BadGymClassException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
    }

    @Test
    public void signUpMemberThrowsGymClassFullCapacityExceptionTest() {
        gymClass.getMembers().add(new Member());

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));

        assertThrows(GymClassFullCapacityException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
    }

    @Test
    public void signUpMemberThrowsBadMemberIdExceptionTest() {
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));
        when(memberRepository.findById(gymClassMemberPostDTO.getMemberId())).thenReturn(
                Optional.empty());

        assertThrows(BadMemberIdException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
    }

    @Test
    public void signUpMemberThrowsMemberAlreadySignedUpExceptionTest() {
        Member member = new Member();
        gymClass.getMembers().add(member);
        gymClass.getGymClassKey().getGymClassType().setCapacity(2);

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));
        when(memberRepository.findById(gymClassMemberPostDTO.getMemberId())).thenReturn(
                Optional.of(member));

        assertThrows(MemberAlreadySignedUpException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
    }

    @Test
    public void signUpMemberThrowsMembershipExpiredExceptionTest() {
        Member member = new Member();
        member.setMembershipExpiryDate(LocalDateTime.now().minusDays(1));

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));
        when(memberRepository.findById(gymClassMemberPostDTO.getMemberId())).thenReturn(
                Optional.of(member));

        assertThrows(MembershipExpiredException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
    }

    @Test
    public void signUpMemberSuccessfullyTest() {
        MembershipType membershipType = new MembershipType();
        membershipType.setId(1);
        membershipType.setName("Monthly");
        membershipType.setDuration(30);
        membershipType.setPrice(100);

        Member member = new Member();
        member.setId(1);
        member.setFirstName("Andrei");
        member.setLastName("Popescu");
        member.setEmail("andrei.popescu@yahoo.com");
        member.setPhoneNumber("0723456789");
        member.setHomeAddress("Str. Mieilor, nr. 1");
        member.setMembershipType(membershipType);
        member.setMembershipExpiryDate(LocalDateTime.now().plusDays(30));

        newGymClass.getMembers().add(member);

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));
        when(memberRepository.findById(gymClassMemberPostDTO.getMemberId())).thenReturn(
                Optional.of(member));
        when(gymClassRepository.save(any(GymClass.class))).thenReturn(newGymClass);

        assertEquals(gymClassService.signupMember(gymClassMemberPostDTO),
                gymClassMapper.toDto(newGymClass));
    }
}
