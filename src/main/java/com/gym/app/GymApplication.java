package com.gym.app;

import com.gym.app.model.GymClass;
import com.gym.app.model.GymClassType;
import com.gym.app.model.Member;
import com.gym.app.model.Trainer;
import com.gym.app.service.completable.InitDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class GymApplication implements CommandLineRunner {
    @Autowired
    private InitDatabaseService initDatabaseService;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (Arrays.asList(applicationContext.getEnvironment().getActiveProfiles())
                .contains("test")) {
            return;
        }

        CompletableFuture<Member> memberCompletableFuture =
                CompletableFuture.supplyAsync(initDatabaseService::initMembershipType)
                        .thenApplyAsync(initDatabaseService::initMember);

        CompletableFuture<GymClassType> gymClassTypeCompletableFuture =
                CompletableFuture.supplyAsync(initDatabaseService::initGymClassType);

        CompletableFuture<Trainer> trainerCompletableFuture =
                CompletableFuture.supplyAsync(initDatabaseService::initTrainer);

        CompletableFuture<GymClass> gymClassCompletableFuture =
                gymClassTypeCompletableFuture.thenCombine(trainerCompletableFuture,
                        initDatabaseService::initGymClass);

        CompletableFuture.allOf(memberCompletableFuture, gymClassCompletableFuture)
                .thenRun(() -> System.out.println("Database initialized!")).join();
    }
}
