package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.FDepartmentDto;
import com.amblessed.universitymanagementsystem.dto.FacultyDto;
import com.amblessed.universitymanagementsystem.entity.Department;
import com.amblessed.universitymanagementsystem.entity.Faculty;
import com.amblessed.universitymanagementsystem.repository.DepartmentRepository;
import com.amblessed.universitymanagementsystem.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final ModelMapper modelMapper;

    public List<FacultyDto> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream().map(this::getFacultyDto).toList();
    }

    private FacultyDto getFacultyDto(Faculty faculty) {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setFaculty(faculty.getFacultyType().getName());
        List<Department> departments = faculty.getDepartments();
        List<FDepartmentDto> departmentDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentDtos.add(new FDepartmentDto(department.getName(), department.getCreatedDate().toLocalDate().toString()));
        }
        facultyDto.setDepartments(departmentDtos);
        return facultyDto;
    }



}
