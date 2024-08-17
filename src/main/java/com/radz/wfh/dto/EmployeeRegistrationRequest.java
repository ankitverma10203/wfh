package com.radz.wfh.dto;

import com.radz.wfh.constant.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRegistrationRequest {
  @Size(min = 5)
  private String name;

  @Size(min = 5)
  private String email;

  @NotNull private Role role;

  @Size(min = 5)
  private String password;
}
