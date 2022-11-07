package com.example.practica.dto.User;

import com.example.practica.entities.Course;

import java.util.Set;
import com.example.practica.utils.enums.role;
import com.example.practica.utils.enums.approval;
public class UserDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private role role;

    private approval approval;

    private Set<Course> courses;


    public approval getApproval() {
        return approval;
    }

    public void setApproval(approval approval) {
        this.approval = approval;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public role getRole() {
        return role;
    }

    public void setRole(role role) {
        this.role = role;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public UserDTO() {
        super();
    }

    public UserDTO(Long id, String name, String lastname, String email, String password, com.example.practica.utils.enums.role role, com.example.practica.utils.enums.approval approval) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.approval = approval;
    }
}
