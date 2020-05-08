package uk.co.edbrook.oauth2.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class UserDetailsManagerConfiguration {

    private final DataSource dataSource;

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }
}
