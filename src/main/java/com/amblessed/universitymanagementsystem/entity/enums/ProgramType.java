package com.amblessed.universitymanagementsystem.entity.enums;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgramType {

    BACHELOR("Bachelor"),
    MASTER("Master"),
    PHD("Doctorate");

    private final String name;
}
