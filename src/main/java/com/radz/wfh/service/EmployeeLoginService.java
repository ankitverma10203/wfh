package com.radz.wfh.service;

import com.radz.wfh.constant.LoginStatus;
import com.radz.wfh.dto.EmployeeLoginRequest;

public interface EmployeeLoginService {
  LoginStatus login(EmployeeLoginRequest employeeLoginRequest);
}
