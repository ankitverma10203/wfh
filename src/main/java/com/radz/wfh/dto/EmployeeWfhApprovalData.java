package com.radz.wfh.dto;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWfhApprovalData {
  private String employeeId;
  private String name;
  private String email;
  private String managerId;
  private WfhType requestType;
  private String requestDate;
  private WfhRequestStatus status;
}
