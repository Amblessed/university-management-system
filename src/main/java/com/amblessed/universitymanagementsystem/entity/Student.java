package com.amblessed.universitymanagementsystem.entity;


/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.embedded.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_student")
@ToString
public class Student extends Auditable {


    @NotNull
    private String matricNumber;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State stateOfOrigin;

    private String address;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "program_id")
    private Program program;

    @NotNull
    private LocalDate admittedDate;

    //Check that enrollment date is at 3 months into future from admitted date.
    private LocalDate enrollmentDate;

    //Set graduationDate to be 4 years after enrollment date
    private LocalDate graduationDate;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    //This is the owner of the relationship
    @ManyToMany
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    //@NotBlank(message = "Please provide your profile pic")
    private String studentImage;


    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


}
