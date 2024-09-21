package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.LecturerDto;
import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.entity.Lecturer;
import com.amblessed.universitymanagementsystem.entity.Student;
import com.amblessed.universitymanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final StateRepository stateRepository;
    private final FacultyRepository facultyRepository;
    private final RoleRepository roleRepository;

    public LecturerDto createLecturer(String department, LecturerDto lecturerDto) {

        log.info("Saving Lecturer: " + lecturerDto);
        Lecturer savedLecturer = lecturerRepository.save(getLecturerFromLecturerDto(department, lecturerDto));
        log.info(savedLecturer.toString());
        return getLecturerDtoFromLecturer(savedLecturer);
    }

    private LecturerDto getLecturerDtoFromLecturer(Lecturer savedLecturer) {
        return new LecturerDto();
    }

    private Lecturer getLecturerFromLecturerDto(String department, LecturerDto lecturerDto) {
        return new Lecturer();
    }
}
