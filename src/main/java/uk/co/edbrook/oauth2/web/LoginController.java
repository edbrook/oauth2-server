package uk.co.edbrook.oauth2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.edbrook.oauth2.domain.AccessToken;
import uk.co.edbrook.oauth2.domain.AccessTokenException;
import uk.co.edbrook.oauth2.domain.LoginService;

import javax.security.auth.login.CredentialException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public AccessToken login(@RequestBody CredentialsRequest credentials) {
        try {
            return loginService.login(credentials.getUsername(), credentials.getPassword());
        } catch (CredentialException | AccessTokenException e) {
            throw new LoginFailureException();
        }
    }
}
