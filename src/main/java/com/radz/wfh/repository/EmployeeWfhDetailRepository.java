package com.radz.wfh.repository;

import com.radz.wfh.constant.WfhRequestStatus;
import com.radz.wfh.constant.WfhType;
import com.radz.wfh.model.EmployeeWfhDetail;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeWfhDetailRepository extends JpaRepository<EmployeeWfhDetail, Long> {

  List<EmployeeWfhDetail> findByEmployeeIdAndWfhType(String employeeId, WfhType wfhType);

  List<EmployeeWfhDetail> findByEmployeeId(String employeeId);

  @Query(
      "select e from EmployeeWfhDetail e Join EmployeeDetail ed on e.employeeId = ed.employeeId where e.status = :wfhRequestStatus and e.employeeDetail.managerId = :approverId")
  List<EmployeeWfhDetail> getWfhRequestsByStatus(
          @Param("approverId") String approverId,
      @Param("wfhRequestStatus") WfhRequestStatus wfhRequestStatus);

  @Modifying
  @Transactional
  @Query(
      "update EmployeeWfhDetail e set e.status = :wfhRequestStatus, e.updatedTimestamp = CURRENT_TIMESTAMP where e.wfhRequestId = :wfhRequestId")
  void updateWfhRequestStatus(
      @Param("wfhRequestId") Long wfhRequestId,
      @Param("wfhRequestStatus") WfhRequestStatus wfhRequestStatus);
}
