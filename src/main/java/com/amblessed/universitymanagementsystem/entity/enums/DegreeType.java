package com.amblessed.universitymanagementsystem.entity.enums;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 18-Sep-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DegreeType {

    BSC("Bachelor of Science"),
    BENG("Bachelor of Engineering"),
    BTECH("Bachelor of Technology"),
    MSC("Master of Science"),
    MENG("Master of Engineering"),
    MTECH("Master of Technology"),
    PHD("Doctor of Philosophy");

    private final String description;
}
