package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeDetailData;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface EmployeeDetailService {
  boolean doesAdminExist();

  List<EmployeeDetailData> getPendingRegisterRequestList();

  Optional<Long> validateRequestedId(String requestedId);

  List<EmployeeDetailData> getManagerDetails();

  boolean updateEmployeeDetail(@Valid EmployeeDetailData employeeDetailData);
}
