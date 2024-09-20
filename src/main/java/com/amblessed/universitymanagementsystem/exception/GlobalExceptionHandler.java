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

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, SQLIntegrityConstraintViolationException.class,
            FileExistsException.class, EmptyFileException.class})
    public ProblemDetail handleConstraintViolationException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(FacultyNotFoundException.class)
    public ProblemDetail handleFacultyNotFoundException(FacultyNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

}
