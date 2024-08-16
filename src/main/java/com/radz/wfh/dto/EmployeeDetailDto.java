package com.radz.wfh.dto;

import com.radz.wfh.constant.Role;
import lombok.Data;

@Data
public class EmployeeDetailDto {
  private String name;
  private Role role;
  private String password;
}
