package com.radz.wfh.service;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeRegistrationRequest;

public interface EmployeeRegistrationService {
  EmployeeStatus register(EmployeeRegistrationRequest employeeDetail);
}
