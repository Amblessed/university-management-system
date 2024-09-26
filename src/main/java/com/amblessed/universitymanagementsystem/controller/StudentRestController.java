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

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api/v1/students")
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping("/all-students")
    public ResponseEntity<StudentResponse> getAllStudents(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                           @RequestParam(name = "pageSize", defaultValue = AppConstants.SIZE, required = false) Integer pageSize,
                                                           @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_FIRSTNAME, required = false) String sortBy,
                                                           @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        log.info("Fetching all students");
        return ResponseEntity.ok(studentService.getAllStudents(pageNumber, pageSize, sortBy, sortDirection));
    }
    
    @PostMapping("/create")
    public ResponseEntity<StudentDto> createStudent(@RequestParam String department,
                                                    @RequestBody StudentDto student) {
        log.info("Creating a student");
        return new ResponseEntity<>(studentService.createStudent(department, student), HttpStatus.CREATED);
    }

    @GetMapping("/student/{matric-number}")
    public ResponseEntity<StudentDto> getStudentByMatricNumber(@PathVariable("matric-number") String matNo) {
        log.info(String.format("Fetching the student with the matric number: %s", matNo));
        return ResponseEntity.ok(studentService.getStudentByMatricNumber(matNo));
    }

    @GetMapping("/year")
    public ResponseEntity<StudentResponse> getAllStudentsByYear(@RequestParam(name = "year") String year,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_FIRSTNAME, required = false) String sortBy,
                                                                 @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        log.info(String.format("Fetching students from Year: %s", year));
        return ResponseEntity.ok(studentService.getAllStudentsFromYear(year, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/state")
    public ResponseEntity<StudentResponse> getAllStudentsByState(@RequestParam(name = "state") String state,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.SIZE, required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_FIRSTNAME, required = false) String sortBy,
                                                                 @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        log.info(String.format("Fetching students from: %s State", StringUtils.capitalize(state)));
        return ResponseEntity.ok(studentService.getStudentsByState(state, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/program")
    public ResponseEntity<StudentResponse> getAllStudentsByProgram(@RequestParam(name = "program") String program,
                                                                   @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                   @RequestParam(name = "pageSize", defaultValue = AppConstants.SIZE, required = false) Integer pageSize,
                                                                   @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_FIRSTNAME, required = false) String sortBy,
                                                                   @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection
    ) {
        log.info(String.format("Fetching all students from Program : %s program", StringUtils.capitalize(program)));
        return ResponseEntity.ok(studentService.getStudentsByProgram(program, pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/department")
    public ResponseEntity<StudentResponse> getAllStudentsByDepartment(@RequestParam(name = "department") String department,
                                                                       @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE, required = false) Integer pageNumber,
                                                                       @RequestParam(name = "pageSize", defaultValue = AppConstants.SIZE, required = false) Integer pageSize,
                                                                       @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY_FIRSTNAME, required = false) String sortBy,
                                                                       @RequestParam(name = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        log.info(String.format("Fetching students from Department: %s", department));
        return ResponseEntity.ok(studentService.getStudentsByDepartment(department, pageNumber, pageSize, sortBy, sortDirection));
    }

    @DeleteMapping("/student/{matric-number}")
    public ResponseEntity<String> deleteStudentByMatricNumber(@PathVariable("matric-number") String matNo) {
        log.info("Deleting student with matric number: " + matNo);
        studentService.deleteStudentByMatricNumber(matNo);
        return new ResponseEntity<>("Student successfully deleted", HttpStatus.OK);
    }

}
