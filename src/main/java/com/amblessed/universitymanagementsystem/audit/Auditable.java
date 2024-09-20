package com.amblessed.universitymanagementsystem.audit;



/*
 * @Project Name: university-management-system
 * @Author: Okechukwu Bright Onwumere
 * @Created: 07-Sep-24
 */


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    protected String createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    protected String lastModifiedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(insertable = false)
    protected LocalDateTime lastModifiedDate;


}
