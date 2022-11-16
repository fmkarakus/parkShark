package com.switchfully.parkshark.service.exceptions;

public class EmailNotValidException extends IllegalArgumentException {
    public EmailNotValidException() {
        super("Email is not valid");
    }
}
