package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.entity.*;
import com.amblessed.universitymanagementsystem.entity.embedded.Person;
import com.amblessed.universitymanagementsystem.entity.enums.*;
import com.amblessed.universitymanagementsystem.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;
    private final PersonRepository personRepository;
    private final StateRepository stateRepository;
    private final FacultyRepository facultyRepository;
    private final RoleRepository roleRepository;


    public StudentDto createStudent(String department, StudentDto studentDto) {

        log.info("Saving StudentDto: " + studentDto);
        Student savedStudent = studentRepository.save(getStudentFromStudentDto(department, studentDto));
        log.info(savedStudent.toString());
        return getStudentDtoFromStudent(savedStudent);
    }


    public List<StudentDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        students.stream().map(this::getStudentDtoFromStudent).forEach(studentDtos::add);
        return studentDtos;
    }

    public List<StudentDto> getStudentsByFaculty(String facultyCode) {
        List<Student> students = studentRepository.findByFaculty_FacultyCode(facultyCode);
        List<StudentDto> studentDtos = new ArrayList<>();
        students.stream().map(this::getStudentDtoFromStudent).forEach(studentDtos::add);
        return studentDtos;
    }

    private Person getPersonFromStudentDto(StudentDto studentDto) {
        Gender gender = Gender.valueOf(studentDto.getGender().toUpperCase());
        return Person.builder()
                .id(null)
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email("test@gmail.com")
                .phoneNumber(studentDto.getPhoneNumber())
                .dateOfBirth(LocalDate.parse(studentDto.getDateOfBirth()))
                .gender(gender)
                .profilePicture(null)
                .build();
    }

    private Program getProgram(StudentDto studentDto) {
        String program = studentDto.getProgram();
        ProgramType programType;
        switch (program) {
            case "Bachelor" -> programType = ProgramType.BACHELOR;
            case "Master" -> programType = ProgramType.MASTER;
            case "Doctorate" -> programType = ProgramType.PHD;
            default -> throw new IllegalArgumentException("Invalid program type: " + program);
        }
        return programRepository.findByProgramType(programType).orElse(null);
    }

    private State getStateFromStudentDto(StudentDto studentDto) {
        String stateName = studentDto.getStateOfOrigin().toUpperCase();
        if (Objects.equals(stateName, "CROSS RIVER") || Objects.equals(stateName, "AKWA IBOM")) {
            stateName = stateName.replace(" ", "_");
        }

        StateEnum stateEnum = StateEnum.valueOf(stateName);
        return stateRepository.findByStateEnum(stateEnum).orElseThrow(() -> new RuntimeException("State not found"));
    }

    private Student getStudentFromStudentDto(String departmentName, StudentDto studentDto) {
        log.info(departmentName);
        List<String> names = Stream.of(departmentName.split(" ")).map(String::toLowerCase).toList();
        departmentName = String.join("-", names);

        Department department = departmentRepository.findByName(departmentName);
        Faculty faculty = facultyRepository.findByDepartmentsIsIn(List.of(department));
        Program program = programRepository.save(getProgram(studentDto));
        State state = stateRepository.save(getStateFromStudentDto(studentDto));
        Role studentRole = roleRepository.findByRoleType(RoleType.STUDENT)
                .orElseGet(() -> {
                    Role newStudentRole = new Role(RoleType.STUDENT);
                    return roleRepository.save(newStudentRole);});



        String departmentCode = department.getDepartmentCode();
        departmentCode = departmentCode.substring(departmentCode.length() - 4);

        Person person = getPersonFromStudentDto(studentDto);

        String matricNumber = getMatricNumber(person, program, departmentName);

        String email = "stu" + matricNumber + "@futo.edu";
        person.setEmail(email);

        Person savedPerson = personRepository.save(person);


        Student student = new Student();

        //Randomly generated year to indicate entry year for the student
        int randomElementIndex = ThreadLocalRandom.current().nextInt(2004, 2023 + 1);


        student.setMatricNumber(randomElementIndex + "/" + departmentCode + "/" + matricNumber);
        student.setPerson(savedPerson);
        student.setStateOfOrigin(state);
        student.setAddress(studentDto.getAddress());
        student.setProgram(program);
        student.setDepartment(department);
        student.setFaculty(faculty);
        student.setRole(studentRole);
        student.setAdmittedDate(LocalDate.of(2023, Month.APRIL, 15));
        student.setEnrollmentDate(student.getAdmittedDate().plusMonths(6));
        student.setGraduationDate(student.getEnrollmentDate().plusYears(4));
        return student;
    }

    /**
     * @return All programs in the Program Repository
     */
    public List<Program> getPrograms() {
        return programRepository.findAll();
    }

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    private String getMatricNumber(Person person, Program program, String departmentName) {
        int number = Math.abs(Objects.hash(person.getFirstName(), person.getLastName(), person.getDateOfBirth(), program.getProgramType(), departmentName));
        String matNumber = String.valueOf(number);
        matNumber = matNumber.substring(matNumber.length() - 7);
        return matNumber;
    }

    private StudentDto getStudentDtoFromStudent(Student student) {
        String program = StringUtils.capitalize(student.getProgram().getProgramType().getName());
        String department = StringUtils.capitalize(student.getDepartment().getName());
        List<String> names = Stream.of(department.split("-")).map(StringUtils::capitalize).toList();
        String departmentName = String.join(" ", names);

        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(student.getPerson().getFirstName());
        studentDto.setLastName(student.getPerson().getLastName());
        studentDto.setEmail(student.getPerson().getEmail());
        studentDto.setPhoneNumber(student.getPerson().getPhoneNumber());
        studentDto.setDateOfBirth(getStringFormattedDate(student.getPerson().getDateOfBirth()));
        studentDto.setStateOfOrigin(student.getStateOfOrigin().getStateEnum().getName());
        studentDto.setAddress(student.getAddress());
        studentDto.setGender(student.getPerson().getGender().getName());
        studentDto.setMatricNumber(student.getMatricNumber());
        studentDto.setProgram(program);
        studentDto.setDepartment(departmentName);
        studentDto.setFaculty(student.getFaculty().getFacultyType().toString());
        studentDto.setAdmittedDate(getStringFormattedDate(student.getAdmittedDate()));
        studentDto.setEnrollmentDate(getStringFormattedDate(student.getEnrollmentDate()));
        studentDto.setGraduationDate(getStringFormattedDate(student.getGraduationDate()));
        return studentDto;
    }


    private String getStringFormattedDate(@NonNull LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }


    public List<StudentDto> getStudentsByState(String state) {
        String stateName = state.toUpperCase();
        if (Objects.equals(stateName, "CROSS RIVER") || Objects.equals(stateName, "AKWA IBOM")) {
            stateName = stateName.replace(" ", "_");
        }
        StateEnum stateEnum = StateEnum.valueOf(stateName);
        State savedState = stateRepository.findByStateEnum(stateEnum).orElseThrow(() -> new IllegalArgumentException("State not found"));
        List<Student> students = studentRepository.findByStateOfOrigin(savedState);
        List<StudentDto> studentDtos = new ArrayList<>();
        students.stream().map(this::getStudentDtoFromStudent).forEach(studentDtos::add);
        return studentDtos;

    }
}
