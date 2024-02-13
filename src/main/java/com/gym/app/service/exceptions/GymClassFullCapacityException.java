package com.gym.app.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
        reason = "Gym class at full capacity, cannot add more members")
public class GymClassFullCapacityException extends RuntimeException {
}
