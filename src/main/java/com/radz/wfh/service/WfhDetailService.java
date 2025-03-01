package com.radz.wfh.service;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.dto.*;

import java.util.List;

public interface WfhDetailService {

  WfhResponse requestWfh(String name, EmployeeWfhData employeeWfhData);

  List<EmployeeWfhDetailData> getEmployeeWfhDetail(String employeeId);

  WfhBalanceInfo getEmployeeWfhBalance(String employeeId);

  List<EmployeeWfhApprovalData> getEmployeePendingWfhRequests(String approverId);

  WfhRequestStatus updateEmployeeWfhRequest(Long wfhRequestId, WfhRequestStatus wfhRequestStatus);
}
