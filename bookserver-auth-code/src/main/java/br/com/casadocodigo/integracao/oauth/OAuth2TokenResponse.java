package br.com.casadocodigo.integracao.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class OAuth2TokenResponse {
    @Getter @Setter
    @JsonProperty("access_token")
    private String accessToken;

    @Getter @Setter
    @JsonProperty("token_type")
    private String tokenType;

    @Getter @Setter
    @JsonProperty("expires_in")
    private String expiresIn;

    @Getter @Setter
    @JsonProperty("refresh_token")
    private String refreshToken;
}
