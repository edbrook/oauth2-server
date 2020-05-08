package uk.co.edbrook.oauth2.domain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    private final OAuthServerProperties config;
    private final KeyPairService keyPairService;

    @Override
    public AccessToken generateToken(String username) {
        try {
            SignedJWT jwt = new SignedJWT(
                    createJwtHeader(),
                    createClaims(username));

            var signer = new RSASSASigner(keyPairService.getRsaPrivateKey());

            jwt.sign(signer);

            var token = jwt.serialize();

            return AccessToken.builder()
                    .accessToken(token)
                    .tokenType("bearer")
                    .expiresIn(config.getAccessTokenTimeout())
                    .build();
        } catch (JOSEException e) {
            throw new AccessTokenException(e);
        }
    }

    private JWSHeader createJwtHeader() {
        return new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID(keyPairService.getKeyId())
                .build();
    }

    private JWTClaimsSet createClaims(String username) {
        Instant iat = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        Instant exp = iat.plus(Duration.ofSeconds(config.getAccessTokenTimeout()));

        return new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date(iat.toEpochMilli()))
                .expirationTime(new Date(exp.toEpochMilli()))
                .claim("scope", "all")
                .build();
    }
}
