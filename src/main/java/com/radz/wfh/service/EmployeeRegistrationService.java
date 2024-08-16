package com.radz.wfh.service;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailDto;

public interface EmployeeRegistrationService {
  EmployeeStatus register(EmployeeDetailDto employeeDetail);
}
