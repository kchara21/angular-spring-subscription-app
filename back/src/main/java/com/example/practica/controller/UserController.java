package com.example.practica.controller;
import com.example.practica.dto.*;
import com.example.practica.dto.User.UserPendingResponse;
import com.example.practica.dto.User.UserResponse;
import com.example.practica.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


// CONTROLADOR PARA LA GESTION DE USUARIOS: crea, suscribe un usuario.
@RestController()
@RequestMapping("/api/users")
public class UserController {

    // Inyeccion del servicio del usuario.
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending")
    public UserPendingResponse findAllUsersPending(){
        return userService.listAllPendingUsers();
    }


    @GetMapping("{idUser}")
    public UserResponse finUserById(@PathVariable(name = "idUser") long idUser){
        return userService.listOneUserById(idUser);
    }


    // Suscribe un usuario a un curso.
    // Recibe como PathVariable: el id del usuario y el id del Curso.
    @PreAuthorize("hasRole('CONSUMER')")
    @PutMapping("{idUser}/suscribe/{idCourse}/courses")
    public ResponseEntity<MessageResponse> suscribeUserToCourse(@PathVariable(name = "idUser") long idUser,
                                                                @PathVariable(name = "idCourse") long idCourse){
        MessageResponse userResponse = userService.suscribeToCourse(idUser,idCourse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


    // Activa usuario.
    // Recibe como PathVariable: el id del usuario.
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{idUser}/accept")
    public ResponseEntity<MessageResponse> acceptUser(@PathVariable(name = "idUser") long idUser){
        MessageResponse userResponse = userService.acceptUser(idUser);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



    // Remueve un usuario por id.
    // PathVariable: userId, id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable(name = "id") long id){
        MessageResponse userMessageResponse = userService.deleteUser(id);

        return new ResponseEntity<>(userMessageResponse,HttpStatus.OK);

    }


    // Suscribe un usuario a un curso.
    // Recibe como PathVariable: el id del usuario y el id del Curso.
    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("{idUser}/unsuscribe/{idCourse}/courses")
    public ResponseEntity<MessageResponse> unsuscribeUserToCourse(@PathVariable(name = "idUser") long idUser,
                                                                  @PathVariable(name = "idCourse") long idCourse){
        MessageResponse userResponse = userService.unsuscribeToCourse(idUser,idCourse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



}
