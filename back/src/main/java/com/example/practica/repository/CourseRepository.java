package com.example.practica.repository;

import com.example.practica.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long>  {
}
