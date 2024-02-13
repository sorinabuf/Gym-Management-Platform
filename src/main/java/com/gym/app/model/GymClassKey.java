package com.gym.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class GymClassKey implements java.io.Serializable {
    @ManyToOne
    @JoinColumn(name = "gym_class_type_id", referencedColumnName = "id")
    private GymClassType gymClassType;

    @Column(name = "start_time")
    private LocalDateTime startTime;
}
