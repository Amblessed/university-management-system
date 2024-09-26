package com.amblessed.universitymanagementsystem.exception;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 09-Sep-24
 */


public class FileExistsException extends RuntimeException{

    public FileExistsException(String message) {
        super(message);
    }
}
