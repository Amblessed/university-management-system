package com.amblessed.universitymanagementsystem.dto;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private String name;
    private String facultyName;
    private String code;

    public DepartmentDto(String departmentName, String departmentCode) {
        this.name = departmentName;
        this.code = departmentCode;
    }
}
