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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFaculty_FacultyCode(String facultyCode);

    List<Student> findByStateOfOrigin(State stateOfOrigin);

    List<Student> findByProgram(Program program);

    List<Student> findByDepartment(Department department);

    @Query("select s from Student s where s.matricNumber = :matricNumber")
    Student findByMatricNumber(String matricNumber);
}
