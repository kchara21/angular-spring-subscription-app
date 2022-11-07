package com.example.practica.service.User;

import com.example.practica.dto.*;
import com.example.practica.dto.User.UserPendingDTO;
import com.example.practica.dto.User.UserPendingResponse;
import com.example.practica.dto.User.UserResponse;
import com.example.practica.entities.Course;
import com.example.practica.entities.User;
import com.example.practica.exceptions.PracticaAppException;
import com.example.practica.exceptions.ResourceNotFoundException;
import com.example.practica.repository.CourseRepository;
import com.example.practica.repository.UserRepository;
import com.example.practica.utils.enums.approval;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponse listOneUserById(long id) {
        User user = userRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",id));

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setName(user.getName() + " " + user.getLastname());
        return userResponse;

    }




    @Override
    public MessageResponse suscribeToCourse(Long userId, Long courseId) {
        Set<Course> courseConsumerSubscribed = new HashSet<>();

        // OBTENER AL CONSUMIDOR
        User consumer = userRepository
                .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        // OBTENER EL CURSO A GUARDAR
        Course newCourse = courseRepository
                .findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course","id",courseId));


        // BUSCAR CURSO A GUARDAR EN EL USUARIO EN CUESTION...
        long countCourseAlreadyExist = consumer.getSubscribeCourses().stream()
                .filter(courseSuscribed -> courseSuscribed.getId() == courseId)
                .count();

        // BUSCAR NUMERO DE CURSOS SUSCRITOS CON MISMO CREADOR DEL CURSO A GUARDAR
        long countCoursesCreatorSubscribed = consumer.getSubscribeCourses().stream()
                .filter(courseSuscribed -> courseSuscribed.getUser().getId() == newCourse.getUser().getId())
                .count();

        // VERIFICAR SI EL USUARIO YA ESTA SUSCRITO A UN CURSO DEL MISMO CREADOR...
        if(countCoursesCreatorSubscribed>0)
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"El usuario ya esta registrado a un curso de este creador");


        // VERIFICAR SI YA ESTA SUSCRITO AL CURSO...
        if(countCourseAlreadyExist>0)
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"El usuario ya esta suscrito a este curso");


        // MANTENIENDO CURSOS SUSCRITOS
        courseConsumerSubscribed.addAll(consumer.getSubscribeCourses());

        // AGREGANDO NUEVO CURSO
        courseConsumerSubscribed.add(newCourse);

        // SETEANDO propiedad del CONSUMIDOR con los cursos actualizados
        consumer.setSubscribeCourses(courseConsumerSubscribed);

        User userUpdated = userRepository.save(consumer);


        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("SE SUSCRIBIO AL CURSO: "+newCourse.getTitle());
        return messageResponse;
    }

    @Override
    public MessageResponse unsuscribeToCourse(Long userId, Long courseId) {

        // OBTENER AL CONSUMIDOR
        User consumer = userRepository
                .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        // GUARDAR EN LISTA TODOS LOS CURSOS A EXCEPCION DEL QUE LLEGA COMO PARAMETRO
        Set<Course> courseConsumerSubscribed = consumer.getSubscribeCourses().stream().filter(course -> course.getId() != courseId).collect(Collectors.toSet());
        consumer.setSubscribeCourses(courseConsumerSubscribed);

        // GUARDAR USUARIO CON LAS SUSCRIPCIONES ACTUALIZADAS
        User userUpdated = userRepository.save(consumer);

        // RETORNAR RESPUESTA
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("SE DESUSCRIBIO DE ESTE CURSO");
        return messageResponse;

    }

    @Override
    public UserPendingResponse listAllPendingUsers() {


        List<User> pedingUsers = userRepository
                .findAll().stream()
                .filter(user -> user.getApproval()
                        .equals(approval.pending))
                .collect(Collectors.toList());

        if(pedingUsers.size()<1)
            throw new PracticaAppException(HttpStatus.NOT_FOUND,"No existen usuarios pendientes por aceptar");


        List<UserPendingDTO> content = pedingUsers.stream().map(user -> mappingDTO(user)).collect(Collectors.toList());

        UserPendingResponse userPendingResponse = new UserPendingResponse();
        userPendingResponse.setContent(content);

        return userPendingResponse;
    }

    @Override
    public MessageResponse deleteUser(long id) {
        User user = userRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",id));

        userRepository.delete(user);

        MessageResponse userDeletedResponse = new MessageResponse();
        userDeletedResponse.setMessage("User deleted successfully");

        return userDeletedResponse;
    }

    @Override
    public MessageResponse acceptUser(long id) {
        User user = userRepository
                .findById(id).orElseThrow(()-> new ResourceNotFoundException("User","id",id));

        user.setApproval(approval.ok);
        User userUpdated = userRepository.save(user);

        MessageResponse userUpdatedResponse = new MessageResponse();
        userUpdatedResponse.setMessage("User actived successfully");

        return userUpdatedResponse;

    }


    private UserPendingDTO mappingDTO(User user){
        UserPendingDTO userPendingDTO = modelMapper.map(user, UserPendingDTO.class);
        return userPendingDTO;
    }


}
