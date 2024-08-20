package com.radz.wfh.service;

import com.radz.wfh.dto.EmployeeWfhData;
import com.radz.wfh.dto.EmployeeWfhDetailData;
import com.radz.wfh.dto.WfhBalanceInfo;
import com.radz.wfh.dto.WfhResponse;

import java.util.List;

public interface WfhDetailService {

  WfhResponse requestWfh(EmployeeWfhData employeeWfhData);

  List<EmployeeWfhDetailData> getEmployeeWfhDetail(Long employeeId);

  WfhBalanceInfo getEmployeeWfhBalance(Long employeeId);
}
