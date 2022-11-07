package com.example.practica.dto.Auth;

import com.example.practica.utils.enums.role;

public class JWTAuthResponseDTO {

    private long id;
    private String accessToken;
    private String tokenType = "Bearer";

    private role role;

    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public com.example.practica.utils.enums.role getRole() {
        return role;
    }

    public void setRole(com.example.practica.utils.enums.role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public JWTAuthResponseDTO() {
        super();
    }

    public JWTAuthResponseDTO(long id, String accessToken, com.example.practica.utils.enums.role role, String username) {
        this.id = id;
        this.accessToken = accessToken;
        this.role = role;
        this.username = username;
    }
}
