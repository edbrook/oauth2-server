package uk.co.edbrook.oauth2.domain;

import javax.security.auth.login.CredentialException;

public interface LoginService {
    AccessToken login(String username, String password) throws CredentialException;
}
