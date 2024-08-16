package com.radz.wfh.repository;

import com.radz.wfh.constant.Role;
import com.radz.wfh.model.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {

  int countByRole(Role role);
}
