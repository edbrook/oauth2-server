package uk.co.edbrook.oauth2.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final OAuthServerProperties config;
    private final ObjectMapper mapper;

    @Override
    public AccessToken generateToken(String username) {
        try {
            JWSObject jwsObject = new JWSObject(
                    createJwtHeader(),
                    createPayload(username));

            jwsObject.sign(new MACSigner(config.getAccessTokenSecret()));

            var token = jwsObject.serialize();

            return AccessToken.builder()
                    .accessToken(token)
                    .tokenType("bearer")
                    .expiresIn(config.getAccessTokenTimeout())
                    .build();
        } catch (JOSEException | JsonProcessingException e) {
            throw new AccessTokenException(e);
        }
    }

    private JWSHeader createJwtHeader() {
        return new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT,
                null, null, null, null, null,
                null, null, null, null, null, null);
    }

    private Payload createPayload(String username) throws JsonProcessingException {
        var claims = createClaims(username);
        return new Payload(mapper.writeValueAsBytes(claims));
    }

    private Map<String, ? extends Serializable> createClaims(String username) {
        Instant iat = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant exp = iat.plus(Duration.ofSeconds(config.getAccessTokenTimeout()));

        return Map.of(
                "sub", username,
                "iat", iat.toEpochMilli() / 1000,
                "exp", exp.toEpochMilli() / 1000,
                "uk.co.edbrook.oauth2", "access",
                "scope", "all");
    }
}
