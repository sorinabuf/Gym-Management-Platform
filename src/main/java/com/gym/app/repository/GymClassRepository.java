package com.gym.app.repository;

import com.gym.app.model.GymClass;
import com.gym.app.model.GymClassKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface GymClassRepository extends JpaRepository<GymClass, GymClassKey> {
    Optional<GymClass> findByGymClassKey_GymClassType_IdAndGymClassKey_StartTime(int gymClassTypeId,
                                                                                 LocalDateTime startTime);
}
