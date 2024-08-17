package com.radz.wfh.repository;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import com.radz.wfh.model.EmployeeDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {

  int countByRole(Role role);

  List<EmployeeDetail> findByStatus(EmployeeStatus employeeStatus);

  @Query("select e.employeeId from EmployeeDetail e where e.email = :email")
  Long getEmployeeIdByEmail(@Param("email") String email);
}
