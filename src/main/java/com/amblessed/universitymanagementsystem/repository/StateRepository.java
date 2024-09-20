package com.amblessed.universitymanagementsystem.repository;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 15-Sep-24
 */


import com.amblessed.universitymanagementsystem.entity.State;
import com.amblessed.universitymanagementsystem.entity.enums.StateEnum;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Integer> {

    Optional<State> findByStateEnum(@NotNull StateEnum stateEnum);
}
