package com.radz.wfh.repository;

import com.radz.wfh.constant.WfhType;
import com.radz.wfh.model.EmployeeWfhDetail;
import com.radz.wfh.model.EmployeeWfhDetailId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeWfhDetailRepository
    extends JpaRepository<EmployeeWfhDetail, EmployeeWfhDetailId> {

  List<EmployeeWfhDetail> findByEmployeeIdAndWfhType(Long employeeId, WfhType wfhType);
}
