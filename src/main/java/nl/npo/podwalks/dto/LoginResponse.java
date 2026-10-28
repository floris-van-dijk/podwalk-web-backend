package nl.npo.podwalks.dto;

public class LoginResponse {
    public String email;
    public String username;
    public String role;
    public String token;

    public LoginResponse(String email, String username, String role, String token) {
        this.email = email;
        this.username = username;
        this.role = role;
        this.token = token;
    }
}
