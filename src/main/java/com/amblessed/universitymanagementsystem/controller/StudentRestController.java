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
        log.info("Fetching a student based on the matric number");
        return ResponseEntity.ok(studentService.getStudentByMatricNumber(matNo));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<StudentDto>> getAllStudentsFromState(@PathVariable(name = "state") String state) {
        log.info(String.format("Fetching students from: %s State", StringUtils.capitalize(state)));
        return ResponseEntity.ok(studentService.getStudentsByState(state));
    }

    @GetMapping("/program/{program}")
    public ResponseEntity<List<StudentDto>> getAllStudentsByProgram(@PathVariable(name = "program") String program) {
        log.info("Fetching students from: " + program.toUpperCase());
        return ResponseEntity.ok(studentService.getStudentsByProgram(program));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<StudentDto>> getAllStudentsByDepartment(@PathVariable(name = "department") String department) {
        log.info("Fetching students from: " + department);
        return ResponseEntity.ok(studentService.getStudentsByDepartment(department));
    }

    @DeleteMapping("/student/{matric-number}")
    public ResponseEntity<String> deleteStudentByMatricNumber(@PathVariable("matric-number") String matNo) {
        log.info("Deleting student with matric number: " + matNo);
        studentService.deleteStudentByMatricNumber(matNo);
        return new ResponseEntity<>("Student successfully deleted", HttpStatus.OK);
    }

}
