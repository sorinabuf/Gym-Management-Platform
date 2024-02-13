package com.gym.app.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad membership type id")
public class BadMembershipTypeIdException extends RuntimeException {
}
