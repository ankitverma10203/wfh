package com.radz.wfh.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeLoginRequest {
  @Size(min = 5)
  private String email;

  @Size(min = 5)
  private String password;
}
