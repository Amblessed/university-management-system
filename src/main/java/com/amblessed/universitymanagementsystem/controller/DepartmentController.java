package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import com.amblessed.universitymanagementsystem.dto.DepartmentDto;
import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/{faculty-name}/add")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody @Valid Department department, @PathVariable(name = "faculty-name") String facultyName) {
        DepartmentDto departmentDto = departmentService.createDepartment(department, facultyName);
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
    }

    @GetMapping("/all-departments")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }

    @GetMapping("/all-departments-in-faculty")
    public ResponseEntity<List<String>> getDepartmentsInAFaculty(@RequestParam("faculty-name") String facultyName) {
        List<String> departmentDtos = departmentService.getAllDepartmentsInAFaculty(facultyName);
        return new ResponseEntity<>(departmentDtos, HttpStatus.OK);
    }
}
