package eu.organicity.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class OrganicityServiceBaseClient {

    protected final RestTemplate restTemplate;
    private String token;
    protected HttpHeaders headers;
    private HttpEntity<String> req;

    public OrganicityServiceBaseClient() {
        this("");
    }

    public OrganicityServiceBaseClient(final String token) {
        this.token = token;
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
    }

    public void clearToken() {
        this.token = "";
        headers.remove(HttpHeaders.AUTHORIZATION);
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
        if (!"".equals(token)) {
            headers.remove(HttpHeaders.AUTHORIZATION);
            headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        }
        req = new HttpEntity<>("", headers);
    }
}
