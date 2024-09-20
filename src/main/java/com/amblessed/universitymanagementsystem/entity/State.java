package com.amblessed.universitymanagementsystem.entity;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 14-Sep-24
 */

import com.amblessed.universitymanagementsystem.entity.enums.StateEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_state")
@Getter
@Setter
public class State implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StateEnum stateEnum;

    @OneToMany(mappedBy = "stateOfOrigin")
    private List<Student> student;

    public State(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    @Override
    public String toString() {
        return stateEnum.toString();
    }
}
