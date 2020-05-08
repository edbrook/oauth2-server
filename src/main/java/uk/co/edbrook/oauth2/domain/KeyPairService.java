package uk.co.edbrook.oauth2.domain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.OctetKeyPair;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.OctetKeyPairGenerator;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Getter
@Service
public class KeyPairService {

    private final String keyId;

    private final OctetKeyPair keyPair;
    private final OctetKeyPair publicKey;

    private final RSAKey rsaPrivateKey;
    private final RSAKey rsaPublicKey;

    public KeyPairService() throws JOSEException {
        keyId = String.valueOf(Instant.now().toEpochMilli());

        keyPair = new OctetKeyPairGenerator(Curve.Ed25519)
                .keyID(keyId)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.EdDSA)
                .generate();

        publicKey = keyPair.toPublicJWK();

        log.info("Created new ed25519 public key: {}", publicKey);
        log.info("Created new ed25519 private key: {}", keyPair);

        rsaPrivateKey = new RSAKeyGenerator(2048)
                .keyID(keyId)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .generate();

        rsaPublicKey = rsaPrivateKey.toPublicJWK();

        log.info("Created new rsa public key: {}", rsaPublicKey);
        log.info("Created new rsa private key: {}", rsaPrivateKey);
    }
}
