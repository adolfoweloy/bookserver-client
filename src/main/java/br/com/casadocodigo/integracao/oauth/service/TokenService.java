package br.com.casadocodigo.integracao.oauth.service;

import br.com.casadocodigo.integracao.oauth.Bookserver;
import br.com.casadocodigo.integracao.oauth.OAuth2Request;
import br.com.casadocodigo.integracao.oauth.OAuth2TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Base64;

/**
 * Classe responsável por solicitar o token de acesso
 * junto ao authorization server
 */
@Service
public class TokenService {

    @Autowired
    private Bookserver bookserver;

    public OAuth2TokenResponse getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        OAuth2Request request = new OAuth2Request();
        request.setRedirectUri(bookserver.getClientDetails().getRedirectUri());
        request.setGrantType("authorization_code");
        request.setCode(code);
        request.setScope(Arrays.asList("read", "write"));

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(request),
                getHeaders(),
                HttpMethod.POST,
                URI.create(bookserver.getEndpoint("/oauth/token")));

        ResponseEntity<OAuth2TokenResponse> responseEntity = restTemplate.exchange(
                requestEntity, OAuth2TokenResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        // isso deve ser tratado de forma melhor (apenas para exemplo)
        throw new RuntimeException("error trying to retrieve access token");
    }

    private MultiValueMap<String, String> getBody(OAuth2Request oAuth2Request) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("grant_type", oAuth2Request.getGrantType());
        request.add("scope", oAuth2Request.getFormattedScopes());
        request.add("code", oAuth2Request.getCode());
        request.add("redirect_uri", oAuth2Request.getRedirectUri());

        return request;
    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new HttpHeaders();

        Bookserver.ClientDetails clientDetails = bookserver.getClientDetails();

        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization",
            httpBasicAuthentication(clientDetails.getClientId(), clientDetails.getClientSecret()));

        return headers;
    }
    private String httpBasicAuthentication(String clientId, String clientSecret) {
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        return "Basic " + encodedCredentials;
    }

}
