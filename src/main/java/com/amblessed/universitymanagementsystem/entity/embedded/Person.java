package com.amblessed.universitymanagementsystem.entity.embedded;



/*
* @Project Name: university-management-system
* @Author: Okechukwu Bright Onwumere
* @Created: 07-Sep-24
*/


import com.amblessed.universitymanagementsystem.entity.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "tbl_person")
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //Insert the image of the Person. Can be Null
    private String profilePicture;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
