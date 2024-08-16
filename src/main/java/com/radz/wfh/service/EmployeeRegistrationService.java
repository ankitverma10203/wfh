package com.radz.wfh.service;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailRequest;

public interface EmployeeRegistrationService {
  EmployeeStatus register(EmployeeDetailRequest employeeDetail);
}
