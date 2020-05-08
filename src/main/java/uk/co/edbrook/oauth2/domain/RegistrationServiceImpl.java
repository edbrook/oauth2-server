package uk.co.edbrook.oauth2.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean register(String username, String password) {
        if (userDetailsManager.userExists(username)) {
            return false;
        }

        userDetailsManager.createUser(
                User.withUsername(username)
                        .password(password)
                        .passwordEncoder(passwordEncoder::encode)
                        .authorities("USER")
                        .build());
        return true;
    }
}
