package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import com.amblessed.universitymanagementsystem.entity.Lecturer;
import com.amblessed.universitymanagementsystem.service.LecturerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {

    private LecturerService lecturerService;


    public String addLecturer(@RequestBody Lecturer lecturer) {
        return null;
    }
}
