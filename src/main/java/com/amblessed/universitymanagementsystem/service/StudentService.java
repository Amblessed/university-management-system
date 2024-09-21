package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.dto.StudentResponse;
import com.amblessed.universitymanagementsystem.entity.*;
import com.amblessed.universitymanagementsystem.entity.embedded.Person;
import com.amblessed.universitymanagementsystem.entity.enums.*;
import com.amblessed.universitymanagementsystem.exception.ResourceNotFoundException;
import com.amblessed.universitymanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public StudentDto getStudentByMatricNumber(String matricNumber) {
        Student student = studentRepository.findByMatricNumber(matricNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No Student with the matric number %s found", matricNumber)));
        return getStudentDtoFromStudent(student);
    }

    public void deleteStudentByMatricNumber(String matricNumber) {
        Student student = studentRepository.findByMatricNumber(matricNumber)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No Student with the matric number %s found", matricNumber)));
        studentRepository.delete(student);
    }


    public StudentResponse getStudentsByState(String state, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        String stateName = state.toUpperCase();
        if (Objects.equals(stateName, "CROSS RIVER") || Objects.equals(stateName, "AKWA IBOM")) {
            stateName = stateName.replace(" ", "_");
        }
        log.info("Fetching students from " + stateName);
        StateEnum stateEnum = StateEnum.valueOf(stateName);

        State savedState = stateRepository.findByStateEnum(stateEnum).orElseThrow(() -> new ResourceNotFoundException(String.format("No State with the name %s found", stateEnum)));

        Sort sortByAndOrder = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortByAndOrder);


        Page<Student> studentPage = studentRepository.findByStateOfOrigin(savedState, pageDetails);
        return getStudentResponse(studentPage);
    }

    public StudentResponse getStudentsByProgram(String programType, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        ProgramType type = ProgramType.valueOf(programType.toUpperCase());
        Program savedProgram = programRepository.findByProgramType(type).orElseThrow(() -> new ResourceNotFoundException(String.format("No Program with the name %s found", programType)));
        log.info("Fetching students from " + savedProgram);

        Sort sortByAndOrder = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNo, pageSize, sortByAndOrder);

        Page<Student> studentPage = studentRepository.findByProgram(savedProgram, pageDetails);
        return getStudentResponse(studentPage);
    }


    public List<StudentDto> getStudentsByDepartment(String departmentName) {

        String[] name = departmentName.split(" ");
        departmentName = String.join("-", name);
        Department savedDepartment = departmentRepository.findByName(departmentName);
        log.info(savedDepartment.toString());
        List<Student> students = studentRepository.findByDepartment(savedDepartment);
        return getStudentDtosFromStudents(students);
    }

    private Person getPersonFromStudentDto(StudentDto studentDto) {
        Gender gender = Gender.valueOf(studentDto.getGender().toUpperCase());
        return Person.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .phoneNumber(studentDto.getPhoneNumber())
                .dateOfBirth(LocalDate.parse(studentDto.getDateOfBirth()))
                .gender(gender)
                .profilePicture(null)
                .build();
    }

    private Program getProgram(String program) {
        switch (program) {
            case "Bachelor" -> {
                return programRepository.findByProgramType(ProgramType.BACHELOR).orElse(null);
            }
            case "Master" -> {
                return programRepository.findByProgramType(ProgramType.MASTER).orElse(null);
            }
            case "Phd" -> {
                return programRepository.findByProgramType(ProgramType.PHD).orElse(null);
            }
            default -> throw new IllegalArgumentException("Invalid program type: " + program);
        }
    }

    private State getStateFromStudentDto(String state) {
        String stateName = state.toUpperCase();
        if (Objects.equals(stateName, "CROSS RIVER") || Objects.equals(stateName, "AKWA IBOM")) {
            stateName = stateName.replace(" ", "_");
        }

        StateEnum stateEnum = StateEnum.valueOf(stateName);
        return stateRepository.findByStateEnum(stateEnum).orElseThrow(() -> new ResourceNotFoundException(String.format("No State with the name %s found", stateEnum)));
    }

    private Student getStudentFromStudentDto(String departmentName, StudentDto studentDto) {
        log.info(departmentName);
        List<String> names = Stream.of(departmentName.split(" ")).map(String::toLowerCase).toList();
        departmentName = String.join("-", names);

        Department department = departmentRepository.findByName(departmentName);

        Faculty faculty = facultyRepository.findByDepartmentsIsIn(List.of(department));

        Program program = programRepository.save(getProgram(studentDto.getProgram()));

        State state = stateRepository.save(getStateFromStudentDto(studentDto.getStateOfOrigin()));

        Role studentRole = roleRepository.findByRoleType(RoleType.STUDENT);


        String departmentCode = department.getDepartmentCode();
        departmentCode = departmentCode.substring(departmentCode.length() - 4);

        Person person = getPersonFromStudentDto(studentDto);

        String matricNumber = getMatricNumber(person, program, departmentName);

        String email = "stu." + departmentCode + "." + matricNumber + "@futo.edu";
        person.setEmail(email);

        Person savedPerson = personRepository.save(person);


        Student student = new Student();

        //Randomly generated year to indicate entry year for the student
        int randomEntryYear = ThreadLocalRandom.current().nextInt(2004, 2023 + 1);


        student.setMatricNumber(randomEntryYear + "-" + departmentCode + "-" + matricNumber);
        student.setPerson(savedPerson);
        student.setStateOfOrigin(state);
        student.setAddress(studentDto.getAddress());
        student.setProgram(program);
        student.setDepartment(department);
        student.setFaculty(faculty);
        student.setRole(studentRole);

        int randomYear = ThreadLocalRandom.current().nextInt(2018, 2023 + 1);
        student.setAdmittedDate(LocalDate.of(randomYear, Month.APRIL, 15));
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
        int number = Math.abs(Objects.hash(person.getFirstName(), person.getLastName(), person.getDateOfBirth(), person.getGender(), program.getProgramType(), departmentName));
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

    private List<StudentDto> getStudentDtosFromStudents(List<Student> students) {
        List<StudentDto> studentDtos = new ArrayList<>();
        students.stream().map(this::getStudentDtoFromStudent).forEach(studentDtos::add);
        return studentDtos;
    }

    private String getStringFormattedDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    public StudentResponse getStudentResponse(Page<Student> studentPage){
        List<Student> students = studentPage.getContent();
        List<StudentDto> allStudentDtos = getStudentDtosFromStudents(students);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(allStudentDtos);
        studentResponse.setPageNumber(studentPage.getNumber());
        studentResponse.setPageSize(studentPage.getSize());
        studentResponse.setTotalElements(studentPage.getTotalElements());
        studentResponse.setTotalPages(studentPage.getTotalPages());
        studentResponse.setLastPage(studentPage.isLast());
        return studentResponse;
    }

}
