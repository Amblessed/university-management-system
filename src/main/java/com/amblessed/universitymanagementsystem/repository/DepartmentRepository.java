package com.amblessed.universitymanagementsystem.repository;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name);

    List<Department> findDepartmentsByFaculty(Faculty faculty);

    @Query("SELECT d FROM Department d ORDER BY d.id DESC LIMIT 1")
    Department findLastEntry();


}
