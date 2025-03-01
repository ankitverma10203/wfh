package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeDetailData;
import jakarta.validation.Valid;
import java.util.List;

public interface EmployeeDetailService {

  List<EmployeeDetailData> getPendingRegisterRequestList();

  List<EmployeeDetailData> getManagerDetails();

  List<EmployeeDetailData> getAdminDetails();

  boolean updateEmployeeDetail(@Valid EmployeeDetailData employeeDetailData);

  EmployeeDetailData getEmployeeDetail(String requestedId);
}
