package com.gym.app.service.audit.gymClass.events;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetGymClassAttendanceEvent extends GymClassEvent {
    private final int numberOfAttendees;

    public GetGymClassAttendanceEvent(int gymClassTypeId, LocalDateTime gymClassStartTime,
                                      int numberOfAttendees) {
        super("GYM CLASS ATTENDANCE CHECK", gymClassTypeId, gymClassStartTime);
        this.numberOfAttendees = numberOfAttendees;
    }
}
