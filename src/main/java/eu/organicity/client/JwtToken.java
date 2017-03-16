package eu.organicity.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtToken {

    private static final long MILLIS_IN_SECONDS = 1000;

    private String access_token;
    private Long expires_in;
    private Long refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private String id_token;
    @JsonProperty("not-before-policy")
    private Long not_before_policy;
    private String session_state;

    private Long expires_at;
    private Long refresh_expires_at;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
        this.expires_at = System.currentTimeMillis() + expires_in * MILLIS_IN_SECONDS;
    }

    public Long getRefresh_expires_in() {
        return refresh_expires_in;
    }

    public void setRefresh_expires_in(Long refresh_expires_in) {
        this.refresh_expires_in = refresh_expires_in;
        this.refresh_expires_at = System.currentTimeMillis() + refresh_expires_in * MILLIS_IN_SECONDS;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public Long getNot_before_policy() {
        return not_before_policy;
    }

    public void setNot_before_policy(Long not_before_policy) {
        this.not_before_policy = not_before_policy;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public boolean isExpired() {
        return expires_at != null && expires_at <= System.currentTimeMillis();
    }

    public boolean isRefreshExpired() {
        return refresh_expires_at != null && refresh_expires_at <= System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "JwtToken{" + "access_token='" + access_token + '\'' + ", isExpired=" + isExpired() + '}';
    }
}
