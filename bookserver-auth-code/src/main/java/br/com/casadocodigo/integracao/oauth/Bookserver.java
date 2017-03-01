package br.com.casadocodigo.integracao.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bookserver")
public class Bookserver {

    @Getter
    @Setter
    private ClientDetails clientDetails;

    @Setter
    private String authorizationServer;

    public String getEndpoint(String path) {

        if (path.startsWith("/")) {
            return authorizationServer + path;
        }

        return authorizationServer + "/" + path;
    }

    public static class ClientDetails {
        @Getter
        @Setter
        private String clientId;

        @Getter
        @Setter
        private String clientSecret;

        @Getter
        @Setter
        private String redirectUri;
    }
}
