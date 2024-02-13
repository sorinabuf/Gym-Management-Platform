package com.gym.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "gym_class_type")
@SequenceGenerator(
        name = "gymClassTypeSequenceGenerator",
        sequenceName = "gym_class_type_sequence",
        allocationSize = 1
)
@Getter @Setter @NoArgsConstructor
public class GymClassType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "gymClassTypeSequenceGenerator")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "intensity_level")
    private String intensityLevel;

    @OneToMany(mappedBy = "gymClassKey.gymClassType")
    private List<GymClass> gymClasses;
}
