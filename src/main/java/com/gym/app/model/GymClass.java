package com.gym.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "gym_class")
@Getter @Setter @NoArgsConstructor
public class GymClass {
    @EmbeddedId
    private GymClassKey gymClassKey;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    private Trainer trainer;

    @ManyToMany
    @JoinTable(name = "gym_class_participant",
            joinColumns = {
                    @JoinColumn(name = "gym_class_type_id",
                            referencedColumnName = "gym_class_type_id"),
                    @JoinColumn(name = "start_time", referencedColumnName = "start_time")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "member_id", referencedColumnName = "id")
            })
    private List<Member> members;
}
