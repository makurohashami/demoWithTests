package com.example.demowithtests.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class ErrorDetails {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of error", example = "2023-03-15 12:36", required = true)
    public Date timestamp;
    @Schema(description = "Message", example = "Resource not found", required = true)
    public String message;
    @Schema(description = "URI", example = "uri=/api/users/4", required = true)
    public String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
