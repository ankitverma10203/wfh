package com.radz.wfh.repository;

import com.radz.wfh.model.EmployeeCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeCredentialRepository extends JpaRepository<EmployeeCredential, Long> {

  @Query(
      "select ec, ed from EmployeeCredential ec inner join EmployeeDetail ed on ec.employeeId = ed.employeeId where ec.employeeId = :employeeId")
  EmployeeCredential getEmployeeDetailAndCredentials(@Param("employeeId") Long employeeId);
}
