package com.amblessed.universitymanagementsystem.dataloader;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.DepartmentDto;
import com.amblessed.universitymanagementsystem.entity.*;
import com.amblessed.universitymanagementsystem.entity.enums.FacultyType;
import com.amblessed.universitymanagementsystem.entity.enums.ProgramType;
import com.amblessed.universitymanagementsystem.entity.enums.RoleType;
import com.amblessed.universitymanagementsystem.entity.enums.StateEnum;
import com.amblessed.universitymanagementsystem.repository.*;
import com.amblessed.universitymanagementsystem.service.DepartmentService;
import com.amblessed.universitymanagementsystem.service.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@RequiredArgsConstructor
public class Dataloader implements ApplicationListener<ContextRefreshedEvent> {
//public class Dataloader implements ApplicationRunner {

    private final RoleService roleService;
    private final FacultyRepository facultyRepository;
    private final ProgramRepository programRepository;
    private final StateRepository stateRepository;
    private final DepartmentService departmentService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedRoles();
        seedFaculty();
        seedProgram();
        seedState();
        seedDepartment();
    }

    private void seedRoles() {
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            Role role = roleService.findByRoleType(roleType);
            if (role == null)
            {
                role = new Role();
                role.setRoleType(roleType);
                roleService.saveRole(role);
            }
        });
    }

    private void seedFaculty() {
        Arrays.stream(FacultyType.values()).forEach(facultyType -> {
            Faculty faculty = facultyRepository.findByFacultyType(facultyType).orElse(null);
            if (faculty == null)
            {
                faculty = new Faculty();
                faculty.setFacultyType(facultyType);
                int number = Math.abs(Objects.hash(facultyType.getName()));
                String facultyCode = String.valueOf(number);
                facultyCode = facultyCode.substring(facultyCode.length() - 4);
                faculty.setFacultyCode(facultyCode);
                facultyRepository.saveAndFlush(faculty);
            }
        });
    }

    private void seedState() {
        Arrays.stream(StateEnum.values()).forEach(stateEnum -> {
            State state = stateRepository.findByStateEnum(stateEnum).orElse(null);
            if (state == null)
            {
                state = new State();
                state.setStateEnum(stateEnum);
                stateRepository.saveAndFlush(state);
            }
        });
    }

    private void seedProgram() {
        Arrays.stream(ProgramType.values()).forEach(programType -> {
            Program program = programRepository.findByProgramType(programType).orElse(null);
            if (program == null)
            {
                program = new Program();
                program.setProgramType(programType);
                programRepository.saveAndFlush(program);
            }
        });
    }

    private void seedDepartment() {

        List<DepartmentDto> dept = departmentService.getAllDepartments();
        if (dept.isEmpty())
        {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<?> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/departments.json");
            HashMap<String, ArrayList<String>> object;
            try {
                object = (HashMap<String, ArrayList<String>>) mapper.readValue(inputStream, typeReference);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (Map.Entry<String, ArrayList<String>> entry : object.entrySet()) {
                String faculty = entry.getKey();
                ArrayList<String> departments = entry.getValue();
                for (String department : departments) {
                    department = department.toLowerCase().replaceAll("\\s+", "-");
                    departmentService.createDepartment(new Department(department), faculty);
                }
            }
        }

    }
}
