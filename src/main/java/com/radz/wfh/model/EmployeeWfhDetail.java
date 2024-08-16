package com.radz.wfh.model;

import com.radz.wfh.constant.WfhConstants;
import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Entity
@Table(name = "EMPLOYEE_WFH_DETAIL")
@Getter
@Setter
@IdClass(EmployeeWfhDetailId.class)
public class EmployeeWfhDetail {

  @Id private Long employeeId;

  @Id private WfhType wfhType;

  @Id private LocalDate requestedWfhDate;

  private WfhRequestStatus status;

  @ManyToOne private EmployeeDetail employeeDetail;

  @Builder.Default
  @Column(nullable = false, updatable = false)
  private String createdBy = WfhConstants.APP_USER;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdTimestamp;

  @Builder.Default
  @Column(nullable = false)
  private String updatedBy = WfhConstants.APP_USER;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedTimestamp;
}
