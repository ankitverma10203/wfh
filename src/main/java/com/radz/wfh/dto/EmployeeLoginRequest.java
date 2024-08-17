package com.radz.wfh.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeLoginRequest {
  @Size(min = 1)
  private String Id;

  @Size(min = 5)
  private String password;
}
