package uk.co.edbrook.oauth2.domain.account;

public interface RegistrationService {
    boolean register(String username, String password);
}
