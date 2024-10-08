package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.configuration.AppConstants;
import com.amblessed.universitymanagementsystem.dto.StudentDto;
import com.amblessed.universitymanagementsystem.dto.StudentResponse;
import com.amblessed.universitymanagementsystem.entity.*;
import com.amblessed.universitymanagementsystem.entity.embedded.Person;
import com.amblessed.universitymanagementsystem.entity.enums.*;
import com.amblessed.universitymanagementsystem.exception.ResourceNotFoundException;
import com.amblessed.universitymanagementsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
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

    public Page<Student> findAll(Pageable pageable) {
        Sort sortByAndOrder = Sort.by(Sort.Direction.ASC, AppConstants.SORT_BY_FIRSTNAME)
                .and(Sort.by(Sort.Direction.ASC, AppConstants.SORT_BY_LASTNAME));
        Pageable pageDetails = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortByAndOrder);
        return studentRepository.findAll(pageDetails);
    }

    public StudentResponse getAllStudents(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, getSort(sortBy, sortDirection));
        Page<Student> studentPage = studentRepository.findAll(pageDetails);

        String path = "all-students?";
        return getStudentResponse(studentPage, path, sortBy, sortDirection);
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

    public StudentResponse getAllStudentsFromYear(String year, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, getSort(sortBy, sortDirection));

        String nextPath = String.format("year?year=%s", year);

        Page<Student> studentPage = studentRepository.findStudentsByYear(year, pageDetails);
        return getStudentResponse(studentPage, nextPath, sortBy, sortDirection);
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

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, getSort(sortBy, sortDirection));

        String nextPath = String.format("state?state=%s", state);

        Page<Student> studentPage = studentRepository.findByStateOfOrigin(savedState, pageDetails);
        return getStudentResponse(studentPage, nextPath, sortBy, sortDirection);
    }

    public StudentResponse getStudentsByProgram(String programType, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        ProgramType type = ProgramType.valueOf(programType.toUpperCase());
        Program savedProgram = programRepository.findByProgramType(type).orElseThrow(() ->
                new ResourceNotFoundException(String.format("No Program with the name %s found", programType)));
        log.info("Fetching students from " + savedProgram.toString());

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, getSort(sortBy, sortDirection));

        String nextPath = String.format("program?program=%s", programType);

        Page<Student> studentPage = studentRepository.findByProgram(savedProgram, pageDetails);
        return getStudentResponse(studentPage, nextPath, sortBy, sortDirection);
    }


    public StudentResponse getStudentsByDepartment(String departmentName, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        String[] name = departmentName.split(" ");
        departmentName = String.join("-", name);
        Department savedDepartment = departmentRepository.findByName(departmentName);
        log.info(savedDepartment.toString());

        Pageable pageDetails = PageRequest.of(pageNo, pageSize, getSort(sortBy,sortDirection));

        String path = String.format("department?department=%s", departmentName);
        Page<Student> studentPage = studentRepository.findByDepartment(savedDepartment, pageDetails);
        return getStudentResponse(studentPage,path, sortBy, sortDirection);
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
                return programRepository.findByProgramType(ProgramType.BACHELOR)
                        .orElseThrow(() -> new ResourceNotFoundException("Program Type Bachelor not found"));
            }
            case "Master" -> {
                return programRepository.findByProgramType(ProgramType.MASTER)
                        .orElseThrow(() -> new ResourceNotFoundException("Program Type Master not found"));
            }
            case "Phd" -> {
                return programRepository.findByProgramType(ProgramType.PHD)
                        .orElseThrow(() -> new ResourceNotFoundException("Program Type Phd not found"));
            }
            default -> throw new IllegalArgumentException("Invalid Program Type passed: " + program);
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

    public StudentResponse getStudentResponse(Page<Student> studentPage, String path, String sortByValue, String direction){
        List<Student> students = studentPage.getContent();

        if(sortByValue.contains(".")){
            sortByValue = sortByValue.substring(sortByValue.indexOf(".")+1);
        }

        String pageSize = String.format("pageSize=%s", studentPage.getSize());
        String sortBy = String.format("sortBy=%s", sortByValue);
        String sortDirection = String.format("sortDirection=%s", direction);

        String baseUrl = "http://localhost:8080/api/v1/students/%s&%s&%s&%s&%s";
        String nextPath;
        if(studentPage.isLast()){
            nextPath = null;
        }
        else{
            String pageNumber = String.format("pageNumber=%s", studentPage.getNumber()+1);
            nextPath = String.format(baseUrl, path, pageNumber, pageSize, sortBy, sortDirection);
        }

        String previousPath;
        if(studentPage.isFirst()){
            previousPath = null;
        }
        else{
            String pageNumber = String.format("pageNumber=%s", studentPage.getNumber()-1);
            previousPath = String.format(baseUrl, path, pageNumber, pageSize, sortBy, sortDirection);
        }
        List<StudentDto> allStudentDtos = getStudentDtosFromStudents(students);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setContent(allStudentDtos);
        studentResponse.setPage(studentPage.getNumber());
        studentResponse.setSize(studentPage.getSize());
        studentResponse.setNext(nextPath);
        studentResponse.setPrevious(previousPath);
        studentResponse.setTotalElements(studentPage.getTotalElements());
        studentResponse.setTotalPages(studentPage.getTotalPages());
        studentResponse.setFirstPage(studentPage.isFirst());
        studentResponse.setLastPage(studentPage.isLast());

        return studentResponse;
    }

    private Sort getSort(String sortBy, @NotNull String sortDirection){

        Sort.Direction direction = sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        return switch (sortBy) {
            case "lastName" -> Sort.by(direction, AppConstants.SORT_BY_LASTNAME)
                    .and(Sort.by(direction, AppConstants.SORT_BY_FIRSTNAME));
            case "state" -> Sort.by(direction, AppConstants.SORT_BY_STATE)
                    .and(Sort.by(direction, AppConstants.SORT_BY_FIRSTNAME))
                    .and(Sort.by(direction, AppConstants.SORT_BY_LASTNAME));
            case "department" -> Sort.by(direction, AppConstants.SORT_BY_DEPARTMENT)
                    .and(Sort.by(direction, AppConstants.SORT_BY_FIRSTNAME))
                    .and(Sort.by(direction, AppConstants.SORT_BY_LASTNAME));
            case null, default -> Sort.by(direction, AppConstants.SORT_BY_FIRSTNAME)
                    .and(Sort.by(direction, AppConstants.SORT_BY_LASTNAME));
        };
    }

}
