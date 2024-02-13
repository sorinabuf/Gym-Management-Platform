package com.gym.app;

import com.gym.app.model.*;
import com.gym.app.repository.GymClassRepository;
import com.gym.app.repository.MemberRepository;
import com.gym.app.service.GymClassService;
import com.gym.app.service.audit.gymClass.events.GetGymClassAttendanceEvent;
import com.gym.app.service.audit.gymClass.events.NewGymClassEvent;
import com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent;
import com.gym.app.service.dto.GymClassMemberPostDTO;
import com.gym.app.service.dto.GymClassPostDTO;
import com.gym.app.service.exceptions.BadGymClassException;
import com.gym.app.service.exceptions.GymClassFullCapacityException;
import com.gym.app.service.mapper.GymClassMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.gym.app.service.audit.gymClass.AuditService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent.SignUpToGymClassEventResult.GYM_CLASS_FULL;
import static com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent.SignUpToGymClassEventResult.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AuditServiceTest {
    @MockBean
    private GymClassRepository gymClassRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private GymClassMapper gymClassMapper;

    @Autowired
    private AuditService auditService;

    @Autowired
    private GymClassService gymClassService;

    private GymClass gymClass;
    private GymClassMemberPostDTO gymClassMemberPostDTO;

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

        auditService.getEvents().clear();
    }

    @Test
    public void auditNewGymClassTest() {
        when(gymClassRepository.save(any(GymClass.class))).thenReturn(
                gymClassMapper.toEntity(any(GymClassPostDTO.class)));

        gymClassService.addGymClass(new GymClassPostDTO());

        assertEquals(1, auditService.getEvents().size());
        assertEquals(NewGymClassEvent.class, auditService.getEvents().get(0).getClass());
    }

    @Test
    public void auditGetGymClassAttendanceTest() {
        int gymClassTypeId = gymClass.getGymClassKey().getGymClassType().getId();
        LocalDateTime gymClassStartTime = gymClass.getGymClassKey().getStartTime();

        gymClass.getMembers().add(new Member());
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassTypeId, gymClassStartTime)).thenReturn(Optional.of(gymClass));

        gymClassService.getGymClass(gymClassTypeId, gymClassStartTime);

        assertEquals(1, auditService.getEvents().size());
        assertEquals(GetGymClassAttendanceEvent.class, auditService.getEvents().get(0).getClass());
        assertEquals(1, ((GetGymClassAttendanceEvent) auditService.getEvents()
                .get(0)).getNumberOfAttendees());
    }

    @Test
    public void auditSignUpToGymClassSuccessTest() {
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

        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                gymClassMemberPostDTO.getGymClassTypeId(),
                gymClassMemberPostDTO.getStartTime())).thenReturn(Optional.of(gymClass));
        when(memberRepository.findById(gymClassMemberPostDTO.getMemberId())).thenReturn(
                Optional.of(member));
        when(gymClassRepository.save(any(GymClass.class))).thenReturn(gymClass);

        gymClassService.signupMember(gymClassMemberPostDTO);

        assertEquals(1, auditService.getEvents().size());
        assertEquals(SignUpToGymClassEvent.class, auditService.getEvents().get(0).getClass());
        assertEquals(SUCCESS,
                ((SignUpToGymClassEvent) auditService.getEvents().get(0)).getResult());
    }

    @Test
    public void auditSignUpToGymClassEventGymClassFullTest() {
        gymClass.getGymClassKey().getGymClassType().setCapacity(0);
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                anyInt(), any())).thenReturn(Optional.of(gymClass));

        assertThrows(GymClassFullCapacityException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
        assertEquals(1, auditService.getEvents().size());
        assertEquals(SignUpToGymClassEvent.class, auditService.getEvents().get(0).getClass());
        assertEquals(GYM_CLASS_FULL,
                ((SignUpToGymClassEvent) auditService.getEvents().get(0)).getResult());
    }

    @Test
    public void auditSignUpToGymClassEventFilterTest() {
        when(gymClassRepository.findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(
                anyInt(), any())).thenReturn(Optional.empty());

        assertThrows(BadGymClassException.class,
                () -> gymClassService.signupMember(gymClassMemberPostDTO));
        assertEquals(0, auditService.getEvents().size());
    }
}
