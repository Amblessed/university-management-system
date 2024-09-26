package com.amblessed.universitymanagementsystem.entity;


import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.enums.ProgramType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;

/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_program")
public class Program extends Auditable {

    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    @OneToMany(mappedBy = "program")
    private List<Student> student;

    public Program(ProgramType programType) {
        this.programType = programType;
    }

    @Override
    public String toString() {
        return String.format("Program: %s", StringUtils.capitalize(programType.toString().toLowerCase()));
    }
}
