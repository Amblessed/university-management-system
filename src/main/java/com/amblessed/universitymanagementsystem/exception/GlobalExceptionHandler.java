package com.amblessed.universitymanagementsystem.exception;

/*
 * @Project Name: springboot-ecommerce
 * @Author: Okechukwu Bright Onwumere
 * @Created: 11-Aug-24
 */

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, SQLIntegrityConstraintViolationException.class,
            FileExistsException.class, EmptyFileException.class})
    public ProblemDetail handleConstraintViolationException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleFacultyNotFoundException(ResourceNotFoundException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", LocalDateTime.now().toString());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("http://localhost:8080/api/v1/common-errors"));
        problemDetail.setTitle(HttpStatus.NOT_FOUND.getReasonPhrase());
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setInstance(problemDetail.getInstance());
        problemDetail.setProperties(map);
        return problemDetail;
    }

}
