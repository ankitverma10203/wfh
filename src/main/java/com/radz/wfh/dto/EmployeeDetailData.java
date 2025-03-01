package com.radz.wfh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailData {
  @JsonProperty("id")
  private String employeeId;

  private String name;
  private Role role;
  private String email;
  private String managerId;
  private EmployeeStatus employeeStatus;
}
