package com.gym.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "trainer")
@Getter @Setter @NoArgsConstructor
public class Trainer extends Person {
    @ElementCollection
    @CollectionTable(name = "trainer_specialization",
            joinColumns = {
                    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
            })
    private List<TrainerSpecialization> specializations;

    @OneToMany(mappedBy = "trainer")
    private List<GymClass> gymClasses;
}
