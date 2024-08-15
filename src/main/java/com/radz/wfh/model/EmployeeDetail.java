package com.radz.wfh.model;

import com.radz.wfh.constant.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "EMPLOYEE_DETAIL")
@Builder
@Getter
@Setter
public class EmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Role role;

    @Builder.Default
    @Column(nullable = false)
    private Long managerId = 0L;

    @OneToMany(mappedBy = "employeeDetail")
    private List<EmployeeWfhDetail> employeeWfhDetailList;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTimestamp;

    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;
}
