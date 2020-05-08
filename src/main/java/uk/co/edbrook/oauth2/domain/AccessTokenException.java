package uk.co.edbrook.oauth2.domain;

public class AccessTokenException extends RuntimeException {
    public AccessTokenException(Exception e) {
        super(e);
    }
}
