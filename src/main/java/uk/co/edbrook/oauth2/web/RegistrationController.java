package uk.co.edbrook.oauth2.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.edbrook.oauth2.domain.AccessToken;
import uk.co.edbrook.oauth2.domain.AccessTokenService;
import uk.co.edbrook.oauth2.domain.RegistrationService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AccessTokenService accessTokenService;

    @PostMapping("/register")
    public AccessToken register(@RequestBody CredentialsRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        if (!registrationService.register(username, password)) {
            throw new RegistrationFailureException();
        }

        return accessTokenService.generateToken(username);
    }
}
