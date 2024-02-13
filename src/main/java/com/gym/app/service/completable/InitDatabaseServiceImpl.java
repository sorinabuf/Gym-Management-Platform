package com.gym.app.service.completable;

import com.gym.app.model.*;
import com.gym.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InitDatabaseServiceImpl implements InitDatabaseService {
    @Autowired
    private MembershipTypeRepository membershipTypeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GymClassTypeRepository gymClassTypeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GymClassRepository gymClassRepository;

    @Override
    public MembershipType initMembershipType() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MembershipType membershipType = new MembershipType();
        membershipType.setName("QUARTERLY");
        membershipType.setDuration(90);
        membershipType.setPrice(300);

        return membershipTypeRepository.save(membershipType);
    }

    @Override
    public Member initMember(MembershipType membershipType) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Member member = new Member();
        member.setFirstName("Andrei");
        member.setLastName("Popescu");
        member.setEmail("andrei.popescu@gmail.com");
        member.setPhoneNumber("0723456789");
        member.setHomeAddress("Str. Mieilor, nr. 1");
        member.setMembershipType(membershipType);
        member.setMembershipExpiryDate(LocalDateTime.now());

        return memberRepository.save(member);
    }

    @Override
    public GymClassType initGymClassType() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GymClassType gymClassType = new GymClassType();
        gymClassType.setName("Zumba");
        gymClassType.setCapacity(15);
        gymClassType.setDuration(60);
        gymClassType.setIntensityLevel("BEGINNER");

        return gymClassTypeRepository.save(gymClassType);
    }

    @Override
    public Trainer initTrainer() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName("Mihai");
        trainer.setLastName("Ionescu");
        trainer.setEmail("mihai.ion@gmail.com");
        trainer.setPhoneNumber("0723456789");

        var specialization = new TrainerSpecialization();
        specialization.setName("Fitness");
        trainer.setSpecializations(List.of(specialization));

        return trainerRepository.save(trainer);
    }

    @Override
    public GymClass initGymClass(GymClassType gymClassType, Trainer trainer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GymClass gymClass = new GymClass();

        var gymClassKey = new GymClassKey();
        gymClassKey.setGymClassType(gymClassType);
        gymClassKey.setStartTime(LocalDateTime.now().plusDays(20));

        gymClass.setGymClassKey(gymClassKey);
        gymClass.setTrainer(trainer);

        return gymClassRepository.save(gymClass);
    }
}
