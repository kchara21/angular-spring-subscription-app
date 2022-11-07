package com.example.practica.service.Course;
import com.example.practica.dto.Course.CourseDTO;
import com.example.practica.dto.Course.CourseResponse;
import com.example.practica.dto.MessageResponse;
import com.example.practica.entities.Course;
import com.example.practica.entities.User;
import com.example.practica.exceptions.PracticaAppException;
import com.example.practica.exceptions.ResourceNotFoundException;
import com.example.practica.repository.CourseRepository;
import com.example.practica.repository.UserRepository;
import com.example.practica.utils.enums.role;
import com.example.practica.utils.enums.status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CourseResponse listAllCourses(long userId, boolean justActive) {
        List<Course> listCourses;
        List<Course> listCoursesSubscribed = new ArrayList<>();


        // OBTENGO AL USUARIO
        User user = userRepository
                .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        // AGREGO TODOS LOS CURSOS SUSCRITOS DEL USUARIO "CONSUMER".
        listCoursesSubscribed.addAll(user.getSubscribeCourses());


        // SI QUIEN DESEA VER LOS CURSOS ES "CONSUMER": MUESTRO SOLO CURSOS ACTIVOS Y NO SUSCRITOS POR EL "CONSUMER"
        if(user.getRole().equals(role.ROLE_CONSUMER)){
            listCourses = courseRepository.findAll().stream()
                    .filter(course ->
                            course.getStatus().equals(status.active) &&
                            !listCoursesSubscribed.contains(course))
                    .collect(Collectors.toList());



            // SI QUIEN DESEA VER LOS CURSOS ES "CREATER": MUESTRO SOLO LOS CURSOS DE ESE CREADOR.
        } else if (user.getRole().equals(role.ROLE_CREATOR)) {
            listCourses = courseRepository.findAll().stream()
                    .filter(courseUser ->
                            courseUser.getUser().getId() == userId)
                    .collect(Collectors.toList());


            // SI SE DESEA TODOS LOS CURSOS SOLO ACTIVOS
        } else if(justActive){
            listCourses = courseRepository.findAll().stream()
                    .filter(course -> course.getStatus().equals(status.active))
                    .collect(Collectors.toList());


            // SI DESEA VER TODOS LOS CURSOS (ACTIVOS, PENDIENTES, INACTIVOS)
        }else{
            listCourses = courseRepository.findAll();
        }

        List<CourseDTO> content  = listCourses.stream().map(course -> mappingDTO(course)).collect(Collectors.toList());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setContent(content);
        return courseResponse;
    }

    @Override
    public CourseResponse listAllPendingCourses() {

        // OBTIENE CURSOS CON EL ESTADO "PENDING"
        List<Course> listCourses = courseRepository.findAll().stream().filter(course -> course.getStatus().equals(status.pending)).collect(Collectors.toList());

        // MAPEA EL CONTENIDO
        List<CourseDTO> content  = listCourses.stream().map(course -> mappingDTO(course)).collect(Collectors.toList());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setContent(content);
        return courseResponse;

    }


    @Override
    public CourseResponse listCoursesActiveByCreater(long userId) {
        List<Course> listActiveCoursesByCreator;

        // FILTRA SOLO CURSOS
        listActiveCoursesByCreator = courseRepository.findAll().stream().filter(courseUser ->
                courseUser.getUser().getId() == userId && courseUser.getStatus().equals(status.active))
                .collect(Collectors.toList());

        List<CourseDTO> content  = listActiveCoursesByCreator.stream().map(courseUser -> mappingDTO(courseUser)).collect(Collectors.toList());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setContent(content);


        return courseResponse;

    }

    @Override
    public CourseDTO createCourse(long userId, CourseDTO courseDTO) {

        User user = userRepository
                .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        Course course = mappingEntity(courseDTO);

        if(course.getStatus().equals(status.active)){
            // BUSCAR NUMERO DE CURSOS ACTIVOS DEL USUARIO "CREADOR"
            long countCoursesCreatorActives = user
                    .getCourses().stream()
                    .filter(course1 -> course1
                            .getStatus()
                            .equals(status.active))
                    .count();

            // VERIFICAR SI YA TIENE 2 CURSOS ACTIVOS
            if(countCoursesCreatorActives == 2)
                throw new PracticaAppException(HttpStatus.BAD_REQUEST,"El usuario Creador ya tiene 2 cursos en linea");
        }

        course.setUser(user);
            Course newCourse = courseRepository.save(course);

        return mappingDTO(newCourse);
    }

    @Override
    public CourseResponse listAllCoursesSuscribed(long userId) {

        // Obtener Usuario
        User user = userRepository
                .findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));

        // Setear los cursos suscritos del usuario
        List<CourseDTO> content  = user.getSubscribeCourses().stream().map(course -> mappingDTO(course)).collect(Collectors.toList());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setContent(content);
        return courseResponse;

    }

    @Override
    public MessageResponse deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course","id",courseId));
        courseRepository.delete(course);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Course deleted successfully");
        return messageResponse;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO, long idCourse) {
        Course course = courseRepository.findById(idCourse).orElseThrow(() -> new ResourceNotFoundException("Course","id",idCourse));

        if(courseDTO.getStatus().equals(status.active)){
            // BUSCAR NUMERO DE CURSOS ACTIVOS DEL USUARIO "CREADOR"
            long countCoursesCreatorActives = course
                    .getUser()
                    .getCourses().stream()
                    .filter(course1 -> course1
                            .getStatus()
                            .equals(status.active))
                    .count();

            // VERIFICAR SI YA TIENE 2 CURSOS ACTIVOS
            if(countCoursesCreatorActives == 2)
                throw new PracticaAppException(HttpStatus.BAD_REQUEST,"El usuario Creador ya tiene 2 cursos en linea");
        }

        course.setTitle(courseDTO.getTitle());
        course.setStatus(courseDTO.getStatus());
        course.setDescription(courseDTO.getDescription());
        Course courseUpdated = courseRepository.save(course);
        return mappingDTO(courseUpdated);
    }


    @Override
    public MessageResponse acceptCourse(Long courseId) {

        // OBTIENE CURSO
        Course courseToAccept = courseRepository
                .findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course","id",courseId));

        // BUSCAR NUMERO DE CURSOS ACTIVOS DEL USUARIO "CREADOR"
        long countCoursesCreatorActives = courseToAccept.getUser()
                .getCourses().stream()
                .filter(course1 -> course1
                        .getStatus()
                        .equals(status.active))
                .count();


        // VERIFICAR SI YA TIENE 2 CURSOS ACTIVOS
        if(countCoursesCreatorActives == 2)
            throw new PracticaAppException(HttpStatus.BAD_REQUEST,"El usuario Creador ya tiene 2 cursos en linea");


        // SE ACEPTA LA SOLICITUD DEL CURSO, SETEA ESTADO A "ACTIVE" Y SE GUARDA EL CURSO ACTUALIZADO
        courseToAccept.setStatus(status.active);
        Course courseUpdated = courseRepository.save(courseToAccept);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("SE ACEPTO LA SOLICITUD AL CURSO: "+courseToAccept.getTitle());
        return messageResponse;

    }

    @Override
    public MessageResponse rejectCourse(Long courseId) {
        // OBTIENE CURSO
        Course course = courseRepository
                .findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course","id",courseId));

        // SETEA STATUS A "INACTIVE"
        course.setStatus(status.inactive);

        Course courseUpdated = courseRepository.save(course);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("SE RECHAZO LA SOLICITUD DE CREACION AL CURSO: "+course.getTitle());
        return messageResponse;
    }


    //Convierte de Entidad a DTO
    private CourseDTO mappingDTO(Course course){
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        return courseDTO;
    }

    //Convierte de DTO a Entidad
    private Course mappingEntity(CourseDTO courseDTO){
        Course course = modelMapper.map(courseDTO,Course.class);
        return course;
    }

}
