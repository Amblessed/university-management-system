package com.amblessed.universitymanagementsystem.entity.enums;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateEnum {

    ABIA("Abia"), ADAMAWA("Adamawa"), AKWA_IBOM("Akwa Ibom"), ANAMBRA("Anambra"),
    BAUCHI("Bauchi"), BAYELSA("Bayelsa"), BENUE("Benue"), BORNO("Borno"), CROSS_RIVER("Cross River"),
    DELTA("Delta"), EBONYI("Ebonyi"), EDO("Edo"), EKITI("Ekiti"), ENUGU("Enugu"),
    GOMBE("Gombe"), IMO("Imo"), JIGAWA("Jigawa"), KADUNA("Kaduna"), KANO("Kano"),
    KATSINA("Katsina"), KEBBI("Kebbi"), KOGI("Kogi"), KWARA("Kwara"), LAGOS("Lagos"),
    NASARAWA("Nasarawa"), NIGER("Niger"), OGUN("Ogun"), ONDO("Ondo"), OSUN("Osun"),
    OYO("Oyo"), PLATEAU("Plateau"), RIVERS("Rivers"), SOKOTO("Sokoto"), TARABA("Taraba"),
    YOBE("Yobe"), ZAMFARA("Zamfara");

    private final String name;
}
