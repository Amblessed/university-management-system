package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 12-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tbl_bank")
@NoArgsConstructor
public class Bank extends Auditable{

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String sortCode;



    public Bank(String sortCode, String name) {
        this.name = name;
        this.sortCode = sortCode;
    }
}
