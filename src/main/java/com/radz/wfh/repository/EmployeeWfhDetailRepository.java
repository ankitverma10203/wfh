package com.radz.wfh.repository;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import com.radz.wfh.model.EmployeeWfhDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeWfhDetailRepository extends JpaRepository<EmployeeWfhDetail, Long> {

  List<EmployeeWfhDetail> findByEmployeeIdAndWfhType(String employeeId, WfhType wfhType);

  List<EmployeeWfhDetail> findByEmployeeId(String employeeId);

  @Query(
      "select e from EmployeeWfhDetail e Join EmployeeDetail ed on e.employeeId = ed.employeeId where e.status = :wfhRequestStatus")
  List<EmployeeWfhDetail> getWfhRequestsByStatus(
      @Param("wfhRequestStatus") WfhRequestStatus wfhRequestStatus);
}
