package uk.co.edbrook.oauth2.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties("oauth2-server")
public class OAuthServerProperties {

    private long authCodeTimeout = 120;

    private long accessTokenTimeout = 3600;
}
