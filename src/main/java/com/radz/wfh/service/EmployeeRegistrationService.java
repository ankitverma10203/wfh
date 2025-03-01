package com.radz.wfh.service;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeInfo;

public interface EmployeeRegistrationService {

  EmployeeStatus register(EmployeeInfo employeeInfo);
}
