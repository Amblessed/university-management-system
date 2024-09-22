package com.amblessed.universitymanagementsystem.dto;

/*
 * @Project Name: springboot-ecommerce
 * @Author: Okechukwu Bright Onwumere
 * @Created: 12-Aug-24
 */


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private List<StudentDto> content;
    private Integer page;
    private Integer size;
    private String next;
    private String previous;
    private Long totalElements;
    private Integer totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
}
