package uk.co.edbrook.oauth2.domain;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
public class KeyStoreProperties {

    @NotBlank
    private String password;

    @NotBlank
    private String path;

    @NotBlank
    private String keyAlias;
}
