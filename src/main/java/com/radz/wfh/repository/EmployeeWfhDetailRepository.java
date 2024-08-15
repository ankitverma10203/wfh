package com.radz.wfh.repository;

import com.radz.wfh.model.EmployeeWfhDetail;
import com.radz.wfh.model.EmployeeWfhDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeWfhDetailRepository extends JpaRepository<EmployeeWfhDetail, EmployeeWfhDetailId> {
}
