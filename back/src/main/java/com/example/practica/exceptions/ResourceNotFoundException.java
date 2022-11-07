package com.example.practica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// Clase que sirve para devolver una estructura personalizada para el tipo de excepcion HttpStatus.NOT_FOUND
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String nameResource;
    private String nameCamp;
    private long valueCamp;

    public ResourceNotFoundException(String nameResource, String nameCamp, long valueCamp) {
        super(String.format("%s not found with %s: %s",nameResource,nameCamp, valueCamp));
        this.nameResource = nameResource;
        this.nameCamp = nameCamp;
        this.valueCamp = valueCamp;
    }

    public String getNameResource() {
        return nameResource;
    }

    public void setNameResource(String nameResource) {
        this.nameResource = nameResource;
    }

    public String getNameCamp() {
        return nameCamp;
    }

    public void setNameCamp(String nameCamp) {
        this.nameCamp = nameCamp;
    }

    public long getValueCamp() {
        return valueCamp;
    }

    public void setValueCamp(long valueCamp) {
        this.valueCamp = valueCamp;
    }
}
