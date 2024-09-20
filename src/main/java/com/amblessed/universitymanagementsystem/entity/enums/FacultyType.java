package com.amblessed.universitymanagementsystem.entity.enums;


/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FacultyType {

    SAAT("School of Agriculture and Agricultural Technology"),
    SBMS("School of Basic Medical Science"),
    SEET("School of Engineering and Engineering Technology"),
    SESET("School of Electrical Systems and Engineering Technology"),
    SICT("School of Information and Communication Technology"),
    SLIT("School of Logistics and Innovation Technology"),
    SOBS("School of Biological Sciences"),
    SOES("School of Environmental Sciences"),
    SOHT("School of Health Technology"),
    SOPS("School of Physical Sciences"),
    SPGS("School of Postgraduate Studies");

    private final String name;
}
