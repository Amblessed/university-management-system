package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 08-Sep-24
 */

import com.amblessed.universitymanagementsystem.audit.Auditable;
import com.amblessed.universitymanagementsystem.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tbl_role")
public class Role extends Auditable {

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
