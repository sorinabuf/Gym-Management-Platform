package com.gym.app.model;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(
        name = "personSequenceGenerator",
        sequenceName = "person_sequence",
        allocationSize = 1
)
@Getter @Setter @NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSequenceGenerator")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
