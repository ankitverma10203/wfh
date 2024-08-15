package com.radz.wfh.model;

import com.radz.wfh.constant.WfhType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "EMPLOYEE_WFH_DETAIL")
@Getter
@Setter
@IdClass(EmployeeWfhDetail.class)
public class EmployeeWfhDetail {

    @Id
    private Long employeeId;

    @Id
    private WfhType wfhType;

    @ColumnDefault("0")
    private int quantityAvailed;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int pendingApproval;

    @ManyToOne
    private EmployeeDetail employeeDetail;

    @Column(nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdTimestamp;

    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedTimestamp;
}
