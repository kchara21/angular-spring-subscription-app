package com.example.practica.service.Course;

import com.example.practica.dto.Course.CourseDTO;
import com.example.practica.dto.Course.CourseResponse;
import com.example.practica.dto.MessageResponse;

public interface CourseService {

    public CourseResponse listAllCourses(long userId, boolean justActive);

    public CourseDTO createCourse(long userId, CourseDTO courseDTO);


    public CourseResponse listAllCoursesSuscribed(long userId);

    public MessageResponse deleteCourse(long courseId);

    public CourseDTO updateCourse(CourseDTO courseDTO, long idCourse);

    public CourseResponse  listAllPendingCourses();



    public CourseResponse  listCoursesActiveByCreater(long userId);

    public MessageResponse acceptCourse(Long courseId );

    public MessageResponse rejectCourse(Long courseId );

}
