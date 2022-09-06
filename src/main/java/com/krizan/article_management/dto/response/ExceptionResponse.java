package com.krizan.article_management.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;

    public ExceptionResponse(HttpStatus status, Throwable ex) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = ex.getMessage();
    }
}
