package com.example.practica.repository;

import com.example.practica.entities.User;
import com.example.practica.utils.enums.role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

    public User findByRole(role role);

    public Boolean existsByEmail(String email);


}
