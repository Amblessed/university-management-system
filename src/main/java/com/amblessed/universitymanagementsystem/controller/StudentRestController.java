package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import com.amblessed.universitymanagementsystem.configuration.AppConstants;
import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.dto.StudentResponse;
import com.amblessed.universitymanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/v1/students")
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        log.info("Fetching all students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @PostMapping("/{department}/create")
    public ResponseEntity<StudentDto> createStudent(@PathVariable String department,
                                                    @RequestBody StudentDto student) {
        log.info("Creating a student");
        return new ResponseEntity<>(studentService.createStudent(department, student), HttpStatus.CREATED);
    }

    /*@GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(@RequestParam(name = "facultyCode", required = false) String facultyCode) {
        log.info("Fetching students based on the faculty code");
        return ResponseEntity.ok(studentService.getStudentsByFaculty(facultyCode));
    }*/

    @GetMapping("/student/{matric-number}")
    public ResponseEntity<StudentDto> getStudentByMatricNumber(@PathVariable("matric-number") String matNo) {
        log.info(String.format("Fetching the student with the matric number: %s", matNo));
        return ResponseEntity.ok(studentService.getStudentByMatricNumber(matNo));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<StudentResponse> getAllStudentsByState(@PathVariable(name = "state") String state,
                                                                    @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                    @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        log.info(String.format("Fetching students from: %s State", StringUtils.capitalize(state)));
        return ResponseEntity.ok(studentService.getStudentsByState(state, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/program/{program}")
    public ResponseEntity<StudentResponse> getAllStudentsByProgram(@PathVariable(name = "program") String program,
                                                                    @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                    @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        log.info(String.format("Fetching all students from: %s program", program.toUpperCase()));
        return ResponseEntity.ok(studentService.getStudentsByProgram(program, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentDto>> getAllStudentsByDepartment(@PathVariable(name = "department") String department) {
        log.info(String.format("Fetching students from: %s", department));
        return ResponseEntity.ok(studentService.getStudentsByDepartment(department));
    }

    @DeleteMapping("/student/{matric-number}")
    public ResponseEntity<String> deleteStudentByMatricNumber(@PathVariable("matric-number") String matNo) {
        log.info("Deleting student with matric number: " + matNo);
        studentService.deleteStudentByMatricNumber(matNo);
        return new ResponseEntity<>("Student successfully deleted", HttpStatus.OK);
    }

}
