package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 12-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.enums.BankName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "tbl_bank")
public class Bank extends Auditable{

    private static final long serialVersionUID = 1L;


    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private BankName name;

    @Column(unique = true, nullable = false)
    private String sortCode;

    @OneToMany
    private Set<Lecturer> lecturers;
}
