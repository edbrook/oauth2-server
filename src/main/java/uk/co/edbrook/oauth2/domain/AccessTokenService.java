package uk.co.edbrook.oauth2.domain;

public interface AccessTokenService {
    AccessToken generateToken(String username);
}
