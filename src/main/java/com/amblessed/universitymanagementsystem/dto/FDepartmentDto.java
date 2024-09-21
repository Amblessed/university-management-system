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
public class FDepartmentDto {

    private String name;
    private String code;

    public FDepartmentDto(String departmentName, String departmentCode) {
        this.name = departmentName;
        this.code = departmentCode;
    }
}
