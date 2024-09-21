package com.amblessed.universitymanagementsystem.dto;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 20-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Course;

import java.util.List;

public class LecturerDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String stateOfOrigin;
    private String address;
    private String gender;
    private String bankAccountNumber;
    private String program;
    private String department;
    private String faculty;
    private List<Course> courses;
    private String employmentDate;
    private String resumptionDate;
    private String retirementDate;
}
