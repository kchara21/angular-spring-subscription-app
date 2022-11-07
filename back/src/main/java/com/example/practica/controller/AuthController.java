package com.example.practica.controller;

import com.example.practica.dto.Auth.JWTAuthResponseDTO;
import com.example.practica.dto.Auth.UserAuthDTO;
import com.example.practica.dto.Auth.UserRegisterDTO;
import com.example.practica.dto.MessageResponse;
import com.example.practica.entities.User;
import com.example.practica.exceptions.PracticaAppException;
import com.example.practica.repository.UserRepository;
import com.example.practica.security.JwtTokenProvider;
import com.example.practica.utils.enums.approval;
import com.example.practica.utils.enums.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authentication(@RequestBody UserAuthDTO userAuthDTO){

        // Obtenemos al usuario con ese token
        User user = userRepository.findByEmail(userAuthDTO.getEmail());

        // Si el usuario aun no ha sido aprobado por el ADMIN entonces no permitir LOGIN
        if((user == null) || user.getApproval().equals(approval.pending)){
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Bad Credentials");
        }

        System.out.println("HOLA");



        Authentication authentication =  authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userAuthDTO.getEmail(),
                        userAuthDTO.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtenemos el token de jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);




        JWTAuthResponseDTO jwtAuthResponseDTO = new JWTAuthResponseDTO();
        jwtAuthResponseDTO.setId(user.getId());
        jwtAuthResponseDTO.setRole(user.getRole());
        jwtAuthResponseDTO.setUsername(user.getName() + " " + user.getLastname());
        jwtAuthResponseDTO.setAccessToken(token);


        return  ResponseEntity.ok(jwtAuthResponseDTO);
    }


    // Crea un nuevo usuario (creator, consumer).
    // Recibe como RequestBody: la informacion del usuario a guardar.
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody UserRegisterDTO userRegisterDTO){


       if(userRepository.existsByEmail(userRegisterDTO.getEmail())){
           throw new PracticaAppException(HttpStatus.BAD_REQUEST,"This email already exist");
       }

       User adminExist = userRepository.findByRole(role.ROLE_ADMIN);
       if(adminExist != null && userRegisterDTO.getRole().equals(role.ROLE_ADMIN))
           throw new PracticaAppException(HttpStatus.BAD_REQUEST,"Admin already exist");


       User user = new User();
       user.setName(userRegisterDTO.getName());
       user.setLastname(userRegisterDTO.getLastname());
       user.setEmail(userRegisterDTO.getEmail());
       user.setRole(userRegisterDTO.getRole());

       if(userRegisterDTO.getRole().equals(role.ROLE_ADMIN)){
           user.setApproval(approval.ok);
       }else{
           user.setApproval(approval.pending);
       }

       user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
       userRepository.save(user);

       MessageResponse messageResponse = new MessageResponse();
       messageResponse.setMessage("User registered successfully!");

       return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }

}
