package com.radz.wfh.model;

import com.radz.wfh.constant.WfhConstants;
import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Entity
@Table(
    name = "EMPLOYEE_WFH_DETAIL",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"employeeId", "wfhType", "requestedWfhDate"})
    })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWfhDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long wfhRequestId;

  private String employeeId;

  @Enumerated(EnumType.STRING)
  private WfhType wfhType;

  private LocalDate requestedWfhDate;

  @Enumerated(EnumType.STRING)
  private WfhRequestStatus status;

  @JoinColumn(
      name = "employeeId",
      referencedColumnName = "employeeId",
      insertable = false,
      updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private EmployeeDetail employeeDetail;

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
