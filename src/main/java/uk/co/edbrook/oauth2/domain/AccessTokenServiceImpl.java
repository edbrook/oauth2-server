package uk.co.edbrook.oauth2.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper;
    private final KeyPairService keyPairService;

    @Override
    public AccessToken generateToken(String username) {
        try {
//            JWSObject jwsObject = new JWSObject(
//                    createJwtHeader(),
//                    createPayload(username));

//            jwsObject.sign(new RSASSASigner(keyPairService.getRsaPrivateKey()));

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
//        } catch (JOSEException | JsonProcessingException e) {
        } catch (JOSEException e) {
            System.out.println(e);
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
                .build();
    }

//    private Payload createPayload(String username) throws JsonProcessingException {
//        var claims = createClaims(username);
//        return new Payload(mapper.writeValueAsBytes(claims));
//    }

//    private Map<String, ? extends Serializable> createClaims(String username) {
//        Instant iat = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//        Instant exp = iat.plus(Duration.ofSeconds(config.getAccessTokenTimeout()));
//
//        return Map.of(
//                "sub", username,
//                "iat", iat.toEpochMilli() / 1000,
//                "exp", exp.toEpochMilli() / 1000,
//                "uk.co.edbrook.oauth2", "access",
//                "scope", "all");
//    }
}
