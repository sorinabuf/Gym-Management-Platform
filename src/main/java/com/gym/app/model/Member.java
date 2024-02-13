package com.gym.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@SecondaryTable(name = "member_detail",
        pkJoinColumns = {
                @PrimaryKeyJoinColumn(name = "member_id", referencedColumnName = "id")
        })
@Getter @Setter @NoArgsConstructor
public class Member extends Person {
    @Column(name = "home_address", table = "member")
    private String homeAddress;

    @ManyToOne
    @JoinColumn(name = "membership_type", referencedColumnName = "id", table = "member_detail")
    private MembershipType membershipType;

    @Column(name = "membership_expiry_date", table = "member_detail")
    private LocalDateTime membershipExpiryDate;

    @ManyToMany(mappedBy = "members")
    private List<GymClass> gymClasses;
}
