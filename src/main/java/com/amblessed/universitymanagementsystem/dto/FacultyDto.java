package com.amblessed.universitymanagementsystem.dto;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import lombok.Data;

import java.util.List;

@Data
public class FacultyDto {

    private String faculty;
    private List<FDepartmentDto> departments;
}
