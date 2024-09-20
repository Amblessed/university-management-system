package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.embedded.Person;
import com.amblessed.universitymanagementsystem.entity.enums.MaritalStatus;
import com.amblessed.universitymanagementsystem.entity.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tbl_lecturer")
public class Lecturer extends Auditable {

    @OneToOne
    private Person person;

    private String address;

    @ManyToOne
    private State stateOfOrigin;

    @NotNull
    @Column(unique = true)
    private String bankAccountNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Bank bank;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @NotNull
    private Date employmentDate;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    //Check that resumptionDate is greater than employmentDate by at least One month
    private Date resumptionDate;

    //First Check that resumptionDate is not null then preform the following check
    //Set as resumptionDate + 35 years or date when age becomes 65
    private Date retirementDate;

    @NotNull
    @OneToMany(mappedBy = "lecturer")
    private Set<Course> courses;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "lecturer_role", joinColumns = @JoinColumn(name = "lecturer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role = Set.of(new Role(RoleType.LECTURER));

}
