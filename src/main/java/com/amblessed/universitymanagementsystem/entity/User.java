package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: spring-security-demo
 * @Author: Okechukwu Bright Onwumere
 * @Created: 05-Sep-24
 */


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "tbl_user")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
}
