package com.example.practica.dto.User;

import com.example.practica.utils.enums.approval;
import com.example.practica.utils.enums.role;

public class UserPendingDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private role role;
    private approval approval;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public com.example.practica.utils.enums.approval getApproval() {
        return approval;
    }

    public void setApproval(com.example.practica.utils.enums.approval approval) {
        this.approval = approval;
    }

    public UserPendingDTO(Long id, String name, String lastname, String email, com.example.practica.utils.enums.role role, com.example.practica.utils.enums.approval approval) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.approval = approval;
    }

    public UserPendingDTO() {
        super();
    }
}
