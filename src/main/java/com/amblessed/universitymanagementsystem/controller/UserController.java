package com.amblessed.universitymanagementsystem.controller;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.User;
import com.amblessed.universitymanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /*@PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }*/

    /*@PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }*/
}
