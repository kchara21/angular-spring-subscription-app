package com.example.practica.dto.Auth;

import com.example.practica.utils.enums.role;

public class UserRegisterDTO {

    private String email;
    private String password;
    private String name;
    private String lastname;
    private role role;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public com.example.practica.utils.enums.role getRole() {
        return role;
    }

    public void setRole(com.example.practica.utils.enums.role role) {
        this.role = role;
    }

    public UserRegisterDTO() {
        super();
    }

    public UserRegisterDTO(String email, String password, String name, String lastname, com.example.practica.utils.enums.role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
    }
}
