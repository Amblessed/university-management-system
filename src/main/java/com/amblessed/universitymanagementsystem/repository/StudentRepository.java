package com.amblessed.universitymanagementsystem.repository;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.entity.Program;
import com.amblessed.universitymanagementsystem.entity.State;
import com.amblessed.universitymanagementsystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFaculty_FacultyCode(String facultyCode);

    @Query("select s from Student s where s.stateOfOrigin = :stateOfOrigin")
    Page<Student> findByStateOfOrigin(@Param("stateOfOrigin")State stateOfOrigin, Pageable pageable);

    Page<Student> findByProgram(Program program, Pageable pageable);

    List<Student> findByDepartment(Department department);

    @Query("select s from Student s where s.matricNumber = :matricNumber")
    Optional<Student> findByMatricNumber(String matricNumber);
}
