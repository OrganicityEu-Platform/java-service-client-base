package eu.organicity.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtToken {
    private String access_token;
    
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    
    public String getAccess_token() {
        return access_token;
    }
}
