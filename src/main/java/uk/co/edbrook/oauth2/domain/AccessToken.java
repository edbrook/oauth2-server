package uk.co.edbrook.oauth2.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"access_token", "token_type", "expires_in", "refresh_token", "scope"})
public class AccessToken {

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("expires_in")
    Long expiresIn;

    @JsonProperty("refresh_token")
    String refreshToken;

    String scope;
}
