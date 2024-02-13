package com.gym.app.service.audit.gymClass;

import com.gym.app.service.audit.gymClass.events.GetGymClassAttendanceEvent;
import com.gym.app.service.audit.gymClass.events.NewGymClassEvent;
import com.gym.app.service.audit.gymClass.events.SignUpToGymClassEvent;
import org.springframework.context.event.EventListener;

public interface Audit {
    @EventListener
    void auditGymClass(NewGymClassEvent newGymClassEvent);

    @EventListener
    void auditGymClass(GetGymClassAttendanceEvent getGymClassAttendanceEvent);

    @EventListener
    void auditGymClass(SignUpToGymClassEvent getGymClassAttendanceEvent);
}
