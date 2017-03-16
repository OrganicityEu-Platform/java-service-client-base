package eu.organicity.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class OrganicityServiceBaseClient {

    protected final RestTemplate restTemplate;
    private final String client_id;
    private final String client_secret;
    private final String username;
    private final String password;
    private JwtToken jwtToken;
    protected HttpHeaders headers;
    protected HttpEntity<String> req;

    public OrganicityServiceBaseClient() {
        this("");
    }


    public OrganicityServiceBaseClient(final String client_id, final String client_secret, final String username, final String password) {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        req = new HttpEntity<>("", headers);

        this.client_id = client_id;
        this.client_secret = client_secret;
        this.username = username;
        this.password = password;

        jwtToken = doGetToken();

        headers.remove(HttpHeaders.AUTHORIZATION);
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken.getAccess_token()));
    }

    public OrganicityServiceBaseClient(final String token) {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        if (!"".equals(token)) {
            headers.remove(HttpHeaders.AUTHORIZATION);
            headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        }
        req = new HttpEntity<>("", headers);

        this.client_id = null;
        this.client_secret = null;
        this.username = null;
        this.password = null;
        this.jwtToken = new JwtToken();
        this.jwtToken.setAccess_token(token);
    }


    public JwtToken doGetToken() {
        return refreshToken();
    }

    public JwtToken refreshTokenIfNeeded() {
        if (jwtToken.isExpired()) {
            jwtToken = refreshToken();
        }

        headers.remove(HttpHeaders.AUTHORIZATION);
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken.getAccess_token()));

        return jwtToken;
    }

    public JwtToken refreshToken() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", client_id);
        params.add("client_secret", client_secret);
        params.add("username", username);
        params.add("password", password);

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        final HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);

        return restTemplate.exchange("https://accounts.organicity.eu/realms/organicity/protocol/openid-connect/token", HttpMethod.POST, req, JwtToken.class).getBody();
    }


    public void clearToken() {
        this.jwtToken.setAccess_token("");
        headers.remove(HttpHeaders.AUTHORIZATION);
    }

    public String getToken() {
        return jwtToken.getAccess_token();
    }

    public void setToken(final String token) {
        this.jwtToken.setAccess_token(token);
        if (!"".equals(token)) {
            headers.remove(HttpHeaders.AUTHORIZATION);
            headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        }
        req = new HttpEntity<>("", headers);
    }
}
