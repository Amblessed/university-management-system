package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.DepartmentDto;
import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.entity.Faculty;
import com.amblessed.universitymanagementsystem.entity.enums.FacultyType;
import com.amblessed.universitymanagementsystem.exception.FacultyNotFoundException;
import com.amblessed.universitymanagementsystem.repository.DepartmentRepository;
import com.amblessed.universitymanagementsystem.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final ModelMapper modelMapper;


    public DepartmentDto createDepartment(Department department, String facultyName) {
        FacultyType facultyType = FacultyType.valueOf(facultyName);
        Faculty faculty = facultyRepository.findByFacultyType(facultyType).orElse(null);
        assert faculty != null;
        String facultyCode = faculty.getFacultyCode();
        int number = Math.abs(Objects.hash(department.getName()));
        String departmentCode = String.valueOf(number);
        departmentCode = departmentCode.substring(departmentCode.length() - 4);

        department.setFaculty(faculty);
        department.setDepartmentCode(facultyCode + departmentCode);
        Department savedDepartment = departmentRepository.save(department);
        DepartmentDto departmentDto = modelMapper.map(savedDepartment, DepartmentDto.class);
        departmentDto.setFacultyName(facultyType.getName());
        departmentDto.setCreatedDate(getFormattedDate(savedDepartment.getCreatedDate()));
        return departmentDto;
    }

    public List<DepartmentDto> getAllDepartments() {
        List<Department> savedDepartments = departmentRepository.findAll();
        return savedDepartments.stream().map(department -> modelMapper.map(department, DepartmentDto.class)).toList();
    }

    public List<String> getAllDepartmentsInAFaculty(String facultyName) {
        FacultyType facultyType;
        try{
            facultyType = FacultyType.valueOf(facultyName.toUpperCase());
        }
        catch(Exception e){
            throw new FacultyNotFoundException(String.format("No Faculty with the name: %s exists", facultyName));
        }

        Faculty faculty = facultyRepository.findByFacultyType(facultyType).orElse(null);
        List<Department> savedDepartments = departmentRepository.findDepartmentsByFaculty(faculty);
        List<String> departments = new ArrayList<>();
        for(Department department : savedDepartments){
            departments.add(department.getName());
        }
        return departments;

    }


    private String getFormattedDate(LocalDateTime dateTime){
        LocalDate date = dateTime.toLocalDate();
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

}
