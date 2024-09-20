package com.amblessed.universitymanagementsystem.dto;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Course;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends BaseDto{

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String stateOfOrigin;
    private String address;
    private String gender;
    private String matricNumber;
    private String program;
    private String department;
    private String faculty;
    private List<Course> courses;
    private String admittedDate;
    private String enrollmentDate;
    private String graduationDate;

}
