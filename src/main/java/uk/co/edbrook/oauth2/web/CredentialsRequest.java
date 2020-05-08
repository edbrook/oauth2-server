package uk.co.edbrook.oauth2.web;

import lombok.Value;

@Value
public class CredentialsRequest {
    String username;
    String password;
}
