package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeLoginRequest;
import com.radz.wfh.dto.LoginResponse;

public interface EmployeeLoginService {
  LoginResponse login(EmployeeLoginRequest employeeLoginRequest);
}
