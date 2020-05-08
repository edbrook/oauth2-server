package uk.co.edbrook.oauth2.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Configuration
@ConfigurationProperties("oauth2-server")
public class OAuthServerProperties {

    private long authCodeTimeout = 120;

    private long accessTokenTimeout = 3600;

    @NotBlank
    @Length(min = 32, max = 256)
    private String authCodeSecret;

    @NotBlank
    @Length(min = 32, max = 256)
    private String accessTokenSecret;
}
