package com.radz.wfh.dto;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeDetailData {
    private Long employeeId;
    private String name;
    private Role role;
    private String email;
    private Long managerId;
    private EmployeeStatus employeeStatus;
}
