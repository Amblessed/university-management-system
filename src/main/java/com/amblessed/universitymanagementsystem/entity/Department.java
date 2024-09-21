package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */


import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_department")

public class Department extends Auditable {

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Lecturer> lecturers;

    @NotNull
    @Column(unique = true)
    private String departmentCode;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Student> students;

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", facultyType=" + faculty.getFacultyType() +
                ", facultyCode=" + faculty.getFacultyCode() +
                ", facultyHead=" + faculty.getFacultyHead() +
                '}';
    }

    public Department(String departmentName) {
        this.name = departmentName;
    }

}
