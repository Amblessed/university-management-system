package com.amblessed.universitymanagementsystem.exception;



/*
 * @Project Name: movies-api
 * @Author: Okechukwu Bright Onwumere
 * @Created: 09-Sep-24
 */


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
