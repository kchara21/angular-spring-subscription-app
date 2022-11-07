package com.example.practica.entities;

import com.example.practica.utils.enums.role;
import com.example.practica.utils.enums.approval;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@SQLUpdate(sql = "/data-mysql.sql")
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String lastname;

    private String email;

    private String password;

    private approval approval;

    private role role;


    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="users_subscriptions",
            joinColumns =
    @JoinColumn(name="user_id", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Course> subscribeCourses = new HashSet<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public com.example.practica.utils.enums.approval getApproval() {
        return approval;
    }

    public void setApproval(com.example.practica.utils.enums.approval approval) {
        this.approval = approval;
    }

    public com.example.practica.utils.enums.role getRole() {
        return role;
    }

    public void setRole(com.example.practica.utils.enums.role role) {
        this.role = role;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Course> getSubscribeCourses() {
        return subscribeCourses;
    }

    public void setSubscribeCourses(Set<Course> subscribeCourses) {
        this.subscribeCourses = subscribeCourses;
    }

    public User() {
        super();
    }

    public User(long id, String name, String lastname, String email, String password, com.example.practica.utils.enums.approval approval, com.example.practica.utils.enums.role role, Set<Course> courses, Set<Course> subscribeCourses) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.approval = approval;
        this.role = role;
        this.courses = courses;
        this.subscribeCourses = subscribeCourses;
    }
}
