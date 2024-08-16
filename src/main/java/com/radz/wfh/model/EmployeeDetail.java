package com.radz.wfh.model;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import com.radz.wfh.constant.WfhConstants;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  private EmployeeStatus status;

  @OneToMany(mappedBy = "employeeDetail")
  private List<EmployeeWfhDetail> employeeWfhDetailList;

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
