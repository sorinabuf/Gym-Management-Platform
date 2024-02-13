package com.gym.app.service.audit.gymClass.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public abstract class GymClassEvent extends ApplicationEvent {
    private final int gymClassTypeId;
    private final LocalDateTime gymClassStartTime;

    public GymClassEvent(Object source, int gymClassTypeId, LocalDateTime gymClassStartTime) {
        super(source);
        this.gymClassTypeId = gymClassTypeId;
        this.gymClassStartTime = gymClassStartTime;
    }
}
