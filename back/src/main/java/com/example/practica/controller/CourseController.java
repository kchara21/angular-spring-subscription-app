package com.example.practica.controller;
import com.example.practica.dto.Course.CourseDTO;
import com.example.practica.dto.Course.CourseResponse;
import com.example.practica.dto.MessageResponse;
import com.example.practica.service.Course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// CONTROLADOR PARA LA GESTION DE CURSOS: listar, guardar, , editar, aceptar, rechazar, remover cursos...
@RestController()
@RequestMapping("/api")
public class CourseController {

    // Inyeccion del servicio del curso.
    @Autowired
    private CourseService courseService;


    // Lista todos los cursos (activos o no), considerando el rol del usuario al filtrar la informacion.
    // Recibe como PathVariable: el id del usuario.
    // Recibe como RequestParam: un parametro no obligatorio que sirve para verificar si filtra solo cursos activos o no.
    @PreAuthorize("hasAnyRole('CONSUMER','CREATOR')")
    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<CourseResponse> listAllCoursesByUserRole(@PathVariable(value = "userId") long userId,
                                         @RequestParam(value = "justActives",defaultValue = "false",required = false) boolean justActive){
        CourseResponse courseResponse = courseService.listAllCourses(userId,justActive);
        return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }


    // Lista todos los cursos suscritos de un usuario.
    // Recibe como PathVariable: el id del usuario.
    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<CourseResponse> listAllSuscribedCoursesByUser(@PathVariable(value = "userId") long userId){
        CourseResponse courseResponse = courseService.listAllCoursesSuscribed(userId);
        return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }


    // Crea curso por usuario "CREATOR".
    // Recibe como PathVariable: el id del usuario.
    // Recibe como RequestBody: la informacion del curso a guardar.
    @PreAuthorize("hasRole('CREATOR')")
    @PostMapping("/users/{userId}/courses")
    public ResponseEntity<CourseDTO> saveCourse(@PathVariable(value = "userId") long userId,
                                                @RequestBody CourseDTO courseDTO){
        return new ResponseEntity<>(courseService.createCourse(userId,courseDTO), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('CREATOR')")
    @PutMapping("/{idCourse}/courses")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO,
                                                  @PathVariable(name = "idCourse") long idCourse){
        CourseDTO courseResponse = courseService.updateCourse(courseDTO,idCourse);
        return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CREATOR')")
    @DeleteMapping("/{courseId}/courses")
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable(value = "courseId") long courseId){
         MessageResponse courseResponse = courseService.deleteCourse(courseId);
         return new ResponseEntity<>(courseResponse,HttpStatus.OK);
    }

}
