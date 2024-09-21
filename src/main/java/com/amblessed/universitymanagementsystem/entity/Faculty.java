package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.enums.FacultyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_faculty")
public class Faculty extends Auditable {

    @Column(unique = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private FacultyType facultyType;

    @Column(unique = true)
    private String facultyCode;

    //@JsonIgnore
    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;

    private String facultyHead;

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyType=" + facultyType +
                ", facultyCode='" + facultyCode + '\'' +
                ", facultyHead='" + facultyHead + '\'' +
                '}';
    }
}
