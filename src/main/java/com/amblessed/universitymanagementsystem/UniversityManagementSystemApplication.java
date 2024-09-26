package com.amblessed.universitymanagementsystem;

import com.amblessed.universitymanagementsystem.dto.DepartmentDto;
import com.amblessed.universitymanagementsystem.entity.*;
import com.amblessed.universitymanagementsystem.entity.enums.FacultyType;
import com.amblessed.universitymanagementsystem.entity.enums.ProgramType;
import com.amblessed.universitymanagementsystem.entity.enums.RoleType;
import com.amblessed.universitymanagementsystem.entity.enums.StateEnum;
import com.amblessed.universitymanagementsystem.repository.FacultyRepository;
import com.amblessed.universitymanagementsystem.repository.ProgramRepository;
import com.amblessed.universitymanagementsystem.repository.StateRepository;
import com.amblessed.universitymanagementsystem.service.BankService;
import com.amblessed.universitymanagementsystem.service.DepartmentService;
import com.amblessed.universitymanagementsystem.service.RoleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversityManagementSystemApplication implements CommandLineRunner {

    private final RoleService roleService;
    private final FacultyRepository facultyRepository;
    private final ProgramRepository programRepository;
    private final StateRepository stateRepository;
    private final DepartmentService departmentService;
    private final BankService bankService;


    public static void main(String[] args) {
        SpringApplication.run(UniversityManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args)  {
        seedRoles();
        seedFaculty();
        seedProgram();
        seedState();
        seedDepartment();
        seedBank();
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
                int number = Math.abs(Objects.hash(facultyType.getName(), facultyType.getName().length()));
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
                state = new State(stateEnum);
                stateRepository.saveAndFlush(state);
            }
        });
    }

    private void seedProgram() {
        Arrays.stream(ProgramType.values()).forEach(programType -> {
            Program program = programRepository.findByProgramType(programType).orElse(null);
            if (program == null)
            {
                program = new Program(programType);
                programRepository.saveAndFlush(program);
            }
        });
    }

    private void seedDepartment()  {

        List<DepartmentDto> savedDepartments = departmentService.getAllDepartments();
        ObjectMapper mapper = new ObjectMapper();
        if (savedDepartments.isEmpty())
        {
            try(InputStream departmentsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("departments.json")){
                JsonNode jsonNode = mapper.readValue(departmentsStream, JsonNode.class);
                jsonNode.fields().forEachRemaining(linkedHashMap -> {
                    String faculty = linkedHashMap.getKey();
                    JsonNode departments = linkedHashMap.getValue();
                    for (JsonNode department: departments){
                        String departmentName = department.asText().toLowerCase().replaceAll("\\s+", "-");
                        departmentService.createDepartment(new Department(departmentName), faculty);
                    }
                });
            }
            catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        }
    }

    private void seedBank()  {

        List<Bank> savedBanks = bankService.getAllBanks();
        ObjectMapper mapper = new ObjectMapper();
        if (savedBanks.isEmpty())
        {
            try(InputStream banksStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("banks.json")){
                JsonNode jsonNodeBank = mapper.readValue(banksStream, JsonNode.class);
                jsonNodeBank.fields().forEachRemaining(linkedHashMap -> {
                    String sortCode = linkedHashMap.getKey();
                    JsonNode bank = linkedHashMap.getValue();
                    String bankName = bank.asText().toLowerCase().replaceAll("\\s+", "-");
                    bankService.createBank(sortCode, bankName);
                });
            }
            catch (Exception e) {
                throw new NullPointerException(e.getMessage());
            }
        }
    }
}
