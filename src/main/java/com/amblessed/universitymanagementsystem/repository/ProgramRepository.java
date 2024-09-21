package com.amblessed.universitymanagementsystem.repository;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 14-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Program;
import com.amblessed.universitymanagementsystem.entity.enums.ProgramType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Integer> {

    Optional<Program> findByProgramType(ProgramType programType);
}
