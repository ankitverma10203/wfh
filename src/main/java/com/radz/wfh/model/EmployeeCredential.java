package com.radz.wfh.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEE_CREDENTIAL")
@Builder
@Getter
@Setter
public class EmployeeCredential {
  @Id private Long employeeId;

  @Column(nullable = false)
  private String password;

  @OneToOne
  private EmployeeDetail employeeDetail;
}
