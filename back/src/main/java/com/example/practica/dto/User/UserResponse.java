package com.example.practica.dto.User;

import com.example.practica.utils.enums.role;

public class UserResponse {
    private String name;
    private String email;
    private role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public com.example.practica.utils.enums.role getRole() {
        return role;
    }

    public void setRole(com.example.practica.utils.enums.role role) {
        this.role = role;
    }

    public UserResponse() {
        super();
    }

    public UserResponse(String name, String email, com.example.practica.utils.enums.role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
