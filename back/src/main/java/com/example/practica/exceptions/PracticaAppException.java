package com.example.practica.exceptions;

import org.springframework.http.HttpStatus;

public class PracticaAppException extends RuntimeException{
    private static final long serialVersionUID = 1l;

    private HttpStatus estate;
    private String message;

    public HttpStatus getEstate() {
        return estate;
    }

    public void setEstate(HttpStatus estate) {
        this.estate = estate;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PracticaAppException(HttpStatus estate, String message) {
        super();
        this.estate = estate;
        this.message = message;
    }

    public PracticaAppException(HttpStatus estate, String message, String message1) {
        super();
        this.estate = estate;
        this.message = message;
        this.message = message1;
    }

}
