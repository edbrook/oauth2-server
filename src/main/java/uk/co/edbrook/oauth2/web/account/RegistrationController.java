package uk.co.edbrook.oauth2.web.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.edbrook.oauth2.domain.AccessToken;
import uk.co.edbrook.oauth2.domain.AccessTokenService;
import uk.co.edbrook.oauth2.domain.account.RegistrationService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AccessTokenService accessTokenService;

    @PostMapping("/register")
    public AccessToken register(@RequestBody CredentialsRequest request) {
        log.info("method=register");

        String username = request.getUsername();
        String password = request.getPassword();

        if (!registrationService.register(username, password)) {
            throw new RegistrationFailureException();
        }

        return accessTokenService.generateToken(username);
    }
}
