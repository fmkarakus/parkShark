package com.switchfully.parkshark.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MissingArgumentException extends IllegalArgumentException{
    public MissingArgumentException(String message) {
        super(message);
    }
}
