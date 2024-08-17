package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeDetailData;

import java.util.List;

public interface EmployeeDetailService {
  boolean doesAdminExist();

  List<EmployeeDetailData> getPendingRegisterRequestList();
}
