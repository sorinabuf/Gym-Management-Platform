package com.gym.app.service.audit.gymClass.events;

import com.gym.app.model.GymClass;
import com.gym.app.model.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpToGymClassEvent extends GymClassEvent {
    private final int memberId;
    private final SignUpToGymClassEventResult result;

    public SignUpToGymClassEvent(int gymClassTypeId, LocalDateTime gymClassStartTime, int memberId,
                                 SignUpToGymClassEventResult result) {
        super("MEMBER SIGN UP TO GYM CLASS", gymClassTypeId, gymClassStartTime);
        this.memberId = memberId;
        this.result = result;
    }

    public enum SignUpToGymClassEventResult {
        SUCCESS,
        GYM_CLASS_NOT_FOUND,
        GYM_CLASS_FULL,
        MEMBER_NOT_FOUND,
        MEMBER_ALREADY_SIGNED_UP,
        MEMBERSHIP_EXPIRED,
    }
}
