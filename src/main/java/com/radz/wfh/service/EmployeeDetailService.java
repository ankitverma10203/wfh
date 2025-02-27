package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeDetailData;
import com.radz.wfh.model.EmployeeDetail;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface EmployeeDetailService {

  List<EmployeeDetailData> getPendingRegisterRequestList();

  List<EmployeeDetailData> getManagerDetails();

  boolean updateEmployeeDetail(@Valid EmployeeDetailData employeeDetailData);

  Optional<EmployeeDetail> getEmployeeDetail(String requestedId);
}
