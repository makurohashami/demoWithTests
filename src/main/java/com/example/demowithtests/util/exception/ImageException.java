package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ImageException extends RuntimeException {

    public ImageException() {
        super("An exceptional situation in photo processing");
    }

    public ImageException(String message) {
        super(Objects.requireNonNullElse(message,
                "An exceptional situation in photo processing"));
    }
}
