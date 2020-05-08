package uk.co.edbrook.oauth2.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenService accessTokenService;

    @Override
    public AccessToken login(String username, String password) throws CredentialException {
        try {
            var encryptedPassword = userDetailsManager.loadUserByUsername(username).getPassword();
            if (!passwordEncoder.matches(password, encryptedPassword)) {
                throw new CredentialException();
            }
            return accessTokenService.generateToken(username);
        } catch (UsernameNotFoundException e) {
            throw new CredentialException();
        }
    }
}
