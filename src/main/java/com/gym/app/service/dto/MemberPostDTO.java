package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class MemberPostDTO {
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String homeAddress;

    private int membershipTypeId;
}
