package com.amblessed.universitymanagementsystem.utilities;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */


import java.util.*;

public class Rough {

    public static void main(String[] args) {
        List<String> faculties = List.of("SAAT", "SBMS", "SOBS", "SEET", "SESET", "SOES", "SOHT", "SICT", "SLIT", "SOPS", "SPGS");
        for (String faculty : faculties) {
            System.out.println(Math.abs(Objects.hash(faculty)));
        }

        System.out.println("***********************************************************");

        List<String> departments = List.of("Agribusiness", "Agricultural Economics", "Agricultural Extension", "Animal Science and Technology",
                "Crop Science Technology", "Fisheries and Aquaculture Technology", "Forestry and Wildlife Technology",
                "Soil Science Technology");
        for (String department : departments) {
            System.out.println(Math.abs(Objects.hash(department)));
        }

        System.out.println("***********************************************************");

        departments = List.of("Computer Engineering", "computer engineering", "Electrical Power Systems Engineering", "Electronics Engineering", "Mechatronics Engineering",
                "Telecommunications Engineering", "Electrical and Electronic Engineering");
        for (String department : departments) {
            int code = Math.abs(Objects.hash(department));
            int length = (int) Math.pow(department.length(), 2);
            System.out.printf("Code is %d, length is %d and code plus length is %d%n", code, length, code+length);
        }
    }
}
