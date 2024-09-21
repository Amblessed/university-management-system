package com.amblessed.universitymanagementsystem.service;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 18-Sep-24
 */

import com.amblessed.universitymanagementsystem.entity.Role;
import com.amblessed.universitymanagementsystem.entity.enums.RoleType;
import com.amblessed.universitymanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log
public class RoleService {


    private final RoleRepository roleRepository;

    /**
     * @param roleType The roleType to search by.
     * @return Role else null
     */
    public Role findByRoleType(RoleType roleType) {
        return roleRepository.findByRoleType(roleType);
    }

    /**
     * @param role The role to be saved in the database.
     *
     */
    public void saveRole(Role role) {
        roleRepository.saveAndFlush(role);
    }


}
