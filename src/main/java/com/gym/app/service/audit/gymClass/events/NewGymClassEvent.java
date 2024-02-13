package com.gym.app.service.audit.gymClass.events;

import com.gym.app.model.GymClass;

import java.time.LocalDateTime;

public class NewGymClassEvent extends GymClassEvent {
    public NewGymClassEvent(int gymClassTypeId, LocalDateTime gymClassStartTime) {
        super("CREATION OF NEW GYM CLASS", gymClassTypeId, gymClassStartTime);
    }
}
