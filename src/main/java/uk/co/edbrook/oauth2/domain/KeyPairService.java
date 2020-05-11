package uk.co.edbrook.oauth2.domain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Slf4j
@Getter
@Service
public class KeyPairService {

    private final String keyId;

    private final RSAKey rsaPrivateKey;
    private final RSAKey rsaPublicKey;

    public KeyPairService(OAuthServerProperties config) throws KeyPairServiceException {
        var keyStoreProperties = config.getKeystore();

        keyId = keyStoreProperties.getKeyAlias();

        char[] ksPassword = keyStoreProperties.getPassword().toCharArray();

        KeyStore keyStore = loadKeyStore(keyStoreProperties.getPath(), ksPassword);
        rsaPrivateKey = loadRsaKey(keyStore, ksPassword, keyId);
        rsaPublicKey = rsaPrivateKey.toPublicJWK();

        log.info("Using RSA public key \"{}\" with SHA256 thumbprint: \"{}\"",
                keyId,
                rsaPublicKey.getX509CertSHA256Thumbprint());
    }

    private KeyStore loadKeyStore(String store, char[] password) throws KeyPairServiceException {
        try {
            File jwkFile = new File(this.getClass().getResource(store).toURI());
            return KeyStore.getInstance(jwkFile, password);
        } catch (IOException
                | CertificateException
                | NoSuchAlgorithmException
                | URISyntaxException
                | KeyStoreException e) {
            log.error("Failed to load key store: {}", store);
            throw new KeyPairServiceException();
        }
    }

    private RSAKey loadRsaKey(KeyStore keyStore, char[] password, String alias) throws KeyPairServiceException {
        try {
            return RSAKey.load(keyStore, alias, password);
        } catch (KeyStoreException | JOSEException e) {
            log.error("Failed to load RSA key from store with alias: \"{}\"", alias);
            throw new KeyPairServiceException();
        }
    }
}
