package com.example.practica.service.User;

import com.example.practica.dto.*;
import com.example.practica.dto.User.UserDTO;
import com.example.practica.dto.User.UserPendingResponse;
import com.example.practica.dto.User.UserResponse;
import com.example.practica.entities.User;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface UserService {

    public UserResponse listOneUserById(long id);

    public MessageResponse suscribeToCourse(Long userId, Long courseId);

    public MessageResponse unsuscribeToCourse(Long userId, Long courseId);


    public UserPendingResponse listAllPendingUsers();


    public MessageResponse deleteUser(long id);

    public MessageResponse acceptUser(long id);



}
