package com.example.practica.dto.Course;
import com.example.practica.utils.enums.status;

public class CourseDTO {
    private long id;
    private String title;
    private String description;
    private status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }

    public CourseDTO(long id, String title, String description, com.example.practica.utils.enums.status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;

    }

    public CourseDTO() {
        super();
    }
}
