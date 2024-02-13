package com.gym.app.service.completable;

import com.gym.app.model.*;

public interface InitDatabaseService {
    MembershipType initMembershipType();

    Member initMember(MembershipType membershipType);

    GymClassType initGymClassType();

    Trainer initTrainer();

    GymClass initGymClass(GymClassType gymClassType, Trainer trainer);
}
