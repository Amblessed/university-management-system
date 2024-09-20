package com.amblessed.universitymanagementsystem.repository;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.entity.Faculty;
import com.amblessed.universitymanagementsystem.entity.enums.FacultyType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByFacultyType(@NotNull FacultyType facultyType);

    Faculty findByDepartmentsIsIn(List<Department> departments);

    @Query("SELECT f FROM Faculty f ORDER BY f.id DESC LIMIT 1")
    Faculty findLastEntry();




}
