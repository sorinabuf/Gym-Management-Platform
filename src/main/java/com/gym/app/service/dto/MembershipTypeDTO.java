package com.gym.app.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class MembershipTypeDTO {
    private int id;

    private String name;

    private int price;

    private int duration;
}
