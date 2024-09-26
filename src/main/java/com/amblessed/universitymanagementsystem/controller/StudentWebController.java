package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 19-Sep-24
 */

import com.amblessed.universitymanagementsystem.configuration.AppConstants;
import com.amblessed.universitymanagementsystem.dto.DepartmentDto;
import com.amblessed.universitymanagementsystem.dto.FacultyDto;
import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.entity.Program;
import com.amblessed.universitymanagementsystem.entity.State;
import com.amblessed.universitymanagementsystem.entity.Student;
import com.amblessed.universitymanagementsystem.entity.enums.ProgramType;
import com.amblessed.universitymanagementsystem.service.DepartmentService;
import com.amblessed.universitymanagementsystem.service.FacultyService;
import com.amblessed.universitymanagementsystem.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
@Log
public class StudentWebController {

    private StudentService studentService;
    private DepartmentService departmentService;
    private FacultyService facultyService;

    @GetMapping("/all-students")
    public String getStudents(Model model, @PageableDefault(size = 25, sort = AppConstants.SORT_BY, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Student> students = studentService.findAll(pageable);
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/add-student")
    public String createStudent(@ModelAttribute StudentDto student, Model model) {
        List<DepartmentDto> departmentDtos = departmentService.getAllDepartments();
        List<String> departments = new java.util.ArrayList<>(List.of());
        for (DepartmentDto department : departmentDtos) {
            List<String> names = Stream.of(department.getName().split("-")).map(StringUtils::capitalize).toList();
            String departmentName = String.join(" ", names);
            departments.add(departmentName);
        }

        List<FacultyDto> faculties = facultyService.getAllFaculties();
        List<Program> programs = studentService.getPrograms();

        List<String> programNames = programs.stream()
                .map(Program::getProgramType)
                .map(ProgramType::getName)
                .toList();

        List<String> genders = List.of("Male", "Female", "other");
        List<State> states = studentService.getAllStates();
        List<String> statesString = states.stream()
                .map(State::toString)
                .map(String::toLowerCase)
                .map(StringUtils::capitalize)
                .toList();

        model.addAttribute("states", statesString);
        model.addAttribute("genders", genders);
        model.addAttribute("faculties", faculties);
        model.addAttribute("programs", programNames);
        model.addAttribute("departments", departments);
        model.addAttribute("student", student);

        return "add-student";
    }

    //2012-6398-3178437


    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") StudentDto student) {
        log.info("Saving a new student in the database: " + student);
        log.info(student.toString());
        String departmentName = student.getDepartment();
        studentService.createStudent(departmentName, student);
        return "redirect:/all-students";
    }


    @GetMapping("/students/delete")
    public String deleteEmployee(@RequestParam("matricNumber") String matricNumber){
        studentService.deleteStudentByMatricNumber(matricNumber);
        return "redirect:/all-students";
    }


}
