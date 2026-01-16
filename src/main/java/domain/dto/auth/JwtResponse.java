package domain.dto.auth;

import java.util.List;

public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private String email;
    private List<String> roles;

    // getters & setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}