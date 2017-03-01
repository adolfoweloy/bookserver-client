package br.com.casadocodigo.integracao.oauth;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OAuth2Request {
    @Getter
    @Setter
    private String grantType;

    @Getter @Setter
    private String code;

    @Getter @Setter
    private String redirectUri;

    @Getter @Setter
    private List<String> scope = new ArrayList<>();

    public String getFormattedScopes() {
        return scope.stream()
            .reduce((s1, s2) -> s1 + " " + s2)
            .map(scopes -> scopes.trim()).get();
    }
}
