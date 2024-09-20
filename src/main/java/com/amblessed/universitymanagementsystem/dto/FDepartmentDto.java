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

    private String departmentName;
    private String createdDate;

    public FDepartmentDto(String departmentName, String createdDate) {
        this.departmentName = departmentName;
        this.createdDate = createdDate;
    }
}
