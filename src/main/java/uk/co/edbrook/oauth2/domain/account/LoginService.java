package uk.co.edbrook.oauth2.domain.account;

import uk.co.edbrook.oauth2.domain.AccessToken;

import javax.security.auth.login.CredentialException;

public interface LoginService {
    AccessToken login(String username, String password) throws CredentialException;
}
