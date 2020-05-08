package uk.co.edbrook.oauth2.web.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Credentials")
public class LoginFailureException extends RuntimeException {
}
