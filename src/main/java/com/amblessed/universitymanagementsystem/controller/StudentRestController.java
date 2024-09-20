package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/v1")
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        log.info("Fetching all students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @PostMapping("/{department}/students")
    public ResponseEntity<StudentDto> createStudent(@PathVariable String department,
                                                    @RequestBody StudentDto student) {
        log.info("Creating a student");
        return new ResponseEntity<>(studentService.createStudent(department, student), HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(@RequestParam(name = "facultyCode", required = false) String facultyCode) {
        log.info("Fetching students based on the faculty code");
        return ResponseEntity.ok(studentService.getStudentsByFaculty(facultyCode));
    }

    @GetMapping("/{state}/students")
    public ResponseEntity<List<StudentDto>> getAllStudentsFromState(@PathVariable(name = "state") String state) {
        log.info("Fetching students from: " + state.toUpperCase());
        return ResponseEntity.ok(studentService.getStudentsByState(state));
    }
}
