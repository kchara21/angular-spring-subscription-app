package com.example.practica.dto.Auth;


import com.example.practica.utils.enums.role;
public class UserAuthResponse {
    private String username;
    private long token;

    private role role;

    public role getRole() {
        return  role;
    }

    public void setRole(role role) {
        this.role = role;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public UserAuthResponse(String username, long token, com.example.practica.utils.enums.role role) {
        this.username = username;
        this.token = token;
        this.role = role;
    }

    public UserAuthResponse() {
        super();
    }
}
