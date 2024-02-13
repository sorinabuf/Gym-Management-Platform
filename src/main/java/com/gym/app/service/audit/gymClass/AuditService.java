package com.gym.app.service.audit.gymClass;

import com.gym.app.service.audit.gymClass.events.GetGymClassAttendanceEvent;
import com.gym.app.service.audit.gymClass.events.GymClassEvent;
import com.gym.app.service.audit.gymClass.events.NewGymClassEvent;
import com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Getter @Service
public class AuditService implements Audit {
    private final List<GymClassEvent> events;

    public AuditService() {
        this.events = new ArrayList<>();
    }

    @Override
    @EventListener
    public void auditGymClass(NewGymClassEvent newGymClassEvent) {
        events.add(newGymClassEvent);
        System.out.println("====================> " + newGymClassEvent.getSource() +
                ": Gym class with type id " + newGymClassEvent.getGymClassTypeId() +
                " and start time " + newGymClassEvent.getGymClassStartTime() + " was created.");
    }

    @Override
    @EventListener
    public void auditGymClass(GetGymClassAttendanceEvent getGymClassAttendanceEvent) {
        events.add(getGymClassAttendanceEvent);
        System.out.println("====================> " + getGymClassAttendanceEvent.getSource() +
                ": Gym class with type id " + getGymClassAttendanceEvent.getGymClassTypeId() +
                " and start time " + getGymClassAttendanceEvent.getGymClassStartTime() + " has " +
                getGymClassAttendanceEvent.getNumberOfAttendees() + " members signed up.");
    }

    @Override
    @EventListener(
            condition =
                    "@eventConditionFilter.isEventInDateRange(#getGymClassAttendanceEvent.gymClassStartTime) " +
                            "and !(#getGymClassAttendanceEvent.result eq T(com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent$SignUpToGymClassEventResult).GYM_CLASS_NOT_FOUND) " +
                            "and !(#getGymClassAttendanceEvent.result eq T(com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent$SignUpToGymClassEventResult).MEMBER_NOT_FOUND)"
    )
    public void auditGymClass(SignUpToGymClassEvent getGymClassAttendanceEvent) {
        events.add(getGymClassAttendanceEvent);
        if (getGymClassAttendanceEvent.getResult() ==
                SignUpToGymClassEvent.SignUpToGymClassEventResult.SUCCESS) {
            System.out.println("====================> " + getGymClassAttendanceEvent.getSource() +
                    ": Member " + getGymClassAttendanceEvent.getMemberId() +
                    " signed up to gym class with type id " +
                    getGymClassAttendanceEvent.getGymClassTypeId() + " and start time " +
                    getGymClassAttendanceEvent.getGymClassStartTime() + ".");
        } else {
            System.out.println("====================> " + getGymClassAttendanceEvent.getSource() +
                    ": Member " + getGymClassAttendanceEvent.getMemberId() +
                    " failed to sign up to gym class with type id " +
                    getGymClassAttendanceEvent.getGymClassTypeId() + " and start time " +
                    getGymClassAttendanceEvent.getGymClassStartTime() + ", with status " +
                    getGymClassAttendanceEvent.getResult() + ".");
        }
    }
}
