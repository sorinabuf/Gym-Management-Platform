package com.gym.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "membership_type")
@SequenceGenerator(
        name = "membershipTypeSequenceGenerator",
        sequenceName = "membership_type_sequence",
        allocationSize = 1
)
@Getter @Setter @NoArgsConstructor
public class MembershipType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "membershipTypeSequenceGenerator")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "duration")
    private int duration;

    @OneToMany(mappedBy = "membershipType")
    private List<Member> users;
}
