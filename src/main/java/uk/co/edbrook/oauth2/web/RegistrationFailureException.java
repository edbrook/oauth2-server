package uk.co.edbrook.oauth2.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Registration failed")
public class RegistrationFailureException extends RuntimeException {
}
