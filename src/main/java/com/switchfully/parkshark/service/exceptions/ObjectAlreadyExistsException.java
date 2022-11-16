package com.switchfully.parkshark.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ObjectAlreadyExistsException extends IllegalArgumentException {
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
