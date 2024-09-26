package com.amblessed.universitymanagementsystem.entity.enums;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 20-Sep-24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankName {

    ACCESS_BANK("Access Bank Plc"),
    ECOBANK("Ecobank Nigeria Plc"),
    ENTERPRISE_BANK("Enterprise Bank"),
    EQUITORIAL_BANK("Equitorial Trust Bank Limited"),
    FIDELITY_BANK("Fidelity Bank Plc"),
    FIRST_BANK("First Bank Of Nigeria Plc"),
    FIRST_CITY_MONUMENT_BANK("First City Monument Bank"),
    FINBANK("Finbank Plc"),
    GUARANTY_TRUST("Guaranty Trust Bank Plc"),
    KEYSTONE_BANK("Keystone Bank"),
    MAINSTREET_BANK("Mainstreet Bank"),
    NIGERIA_INTERNATIONAL_BANK("Nigeria International Bank"),
    //OCEANIC_BANK_INTERNATIONAL("Oceanic Bank International Plc"),
    SKYE_BANK("Polaris Bank Plc"),
    STANBIC_IBTC_BANK("Stanbic IBTC Bank"),
    STANDARD_CHARTERED_BANK("Standard Chartered Bank"),
    STERLING_BANK("Sterling Bank Plc"),
    UNITED_BANK_FOR_AFRICA("United Bank for Africa Plc"),
    UNION_BANK_OF_NIGERIA("Union Bank Of Nigeria Plc"),
    UNITY_BANK("Unity Bank Plc"),
    WEMA_BANK("Wema Bank Plc"),
    ZENITH_BANK("Zenith Bank Plc");


    private final String bank;
}
