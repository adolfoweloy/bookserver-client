package br.com.casadocodigo.integracao.oauth.service;

import br.com.casadocodigo.integracao.oauth.Bookserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Classe responsavel por construir o authorizationServer de autorizacao
 */
@Service
public class AutorizacaoService {

    @Autowired
    private Bookserver bookserver;

    /**
     * Obtem o authorizationServer de autorização para os escopos fornecidos
     * @param scopes
     * @return
     */
    public String getEndpointDeAutorizacao(String scopes) {

        StringBuilder sb = new StringBuilder();

        sb
            .append(bookserver.getEndpoint("oauth/authorize"))
            .append("?")
            .append(buildAuthorizationEndpointQueryComponents(scopes));

        return sb.toString();
    }

    /**
     * Constrói os query parameters do authorizationServer de autorização.
     * com os valores codificados com x-www-form-urlencoded de acordo com a especificação
     * https://tools.ietf.org/html/rfc6749#section-3.1.2
     *
     * @return String
     */
    private String buildAuthorizationEndpointQueryComponents(String scopes) {

        Bookserver.ClientDetails clientDetails = bookserver.getClientDetails();

        String clientId = clientDetails.getClientId();
        String responseType = "code";
        String redirectUri = clientDetails.getRedirectUri();

        StringBuilder sb = new StringBuilder();
        sb.append("response_type").append("=").append(encode(responseType)).append("&")
                .append("client_id").append("=").append(encode(clientId)).append("&")
                .append("redirect_uri").append("=").append(encode(redirectUri)).append("&")
                .append("scope").append("=").append(encode(scopes));

        return sb.toString();
    }

    /**
     * Codifica os valores com x-www-form-urlencoded.
     * A classe URLEncoder utiliza esse padrão para realizar a codificação.
     *
     * @param content
     * @return conteúdo codificado com x-www-form-urlencoded
     */
    private String encode(String content) {
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }

}
