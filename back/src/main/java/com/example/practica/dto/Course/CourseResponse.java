package com.example.practica.dto.Course;

import java.util.List;

public class CourseResponse {
    private List<CourseDTO> content;

    public List<CourseDTO> getContent() {
        return content;
    }

    public void setContent(List<CourseDTO> content) {
        this.content = content;
    }

    public CourseResponse() {
        super();
    }
}
