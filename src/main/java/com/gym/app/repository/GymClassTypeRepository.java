package com.gym.app.repository;

import com.gym.app.model.GymClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymClassTypeRepository extends JpaRepository<GymClassType, Integer> {
}
