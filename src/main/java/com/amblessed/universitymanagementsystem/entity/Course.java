package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */


import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.enums.CourseUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_courses")
public class Course extends Auditable {

    @NotNull
    @Column(unique = true)
    private String courseName;

    @NotNull
    @Column(unique = true)
    private String courseCode;

    @NotNull
    @Column(unique = true)
    private String courseDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseUnit courseUnit;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;


    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
