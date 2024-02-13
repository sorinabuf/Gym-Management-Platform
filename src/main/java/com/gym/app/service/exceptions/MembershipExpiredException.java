package com.gym.app.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Membership expired")
public class MembershipExpiredException extends RuntimeException {
}
