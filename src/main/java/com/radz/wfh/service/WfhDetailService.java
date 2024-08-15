package com.radz.wfh.service;

import com.radz.wfh.constant.Role;
import com.radz.wfh.model.EmployeeDetail;

public interface WfhDetailService {
    EmployeeDetail saveEmployeeDetail(String name, Role role);
}
