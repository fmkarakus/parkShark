package com.switchfully.parkshark.api;

import com.switchfully.parkshark.security.UserAlreadyExistsException;
import com.switchfully.parkshark.service.exceptions.EmailNotValidException;
import com.switchfully.parkshark.service.exceptions.ObjectAlreadyExistsException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class, UserAlreadyExistsException.class, EmailNotValidException.class, ObjectAlreadyExistsException.class})
    protected void badRequestExceptions(Exception exception, HttpServletResponse response) throws IOException {
        log.error(exception.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    protected void PSQLExceptions(PSQLException exception, HttpServletResponse response) throws IOException {
        log.error(exception.getMessage());
        response.sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
    }
}
