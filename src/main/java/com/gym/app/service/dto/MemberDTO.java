package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class MemberDTO {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String homeAddress;

    private MembershipTypeDTO membershipType;

    private LocalDateTime membershipExpiryDate;
}
