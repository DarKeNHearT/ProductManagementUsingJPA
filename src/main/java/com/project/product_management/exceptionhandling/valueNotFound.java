package com.project.product_management.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class valueNotFound extends Exception {
    public valueNotFound(String message){
        super(message);
    }
}
