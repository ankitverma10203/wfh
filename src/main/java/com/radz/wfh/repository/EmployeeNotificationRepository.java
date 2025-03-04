package com.radz.wfh.repository;

import com.radz.wfh.model.EmployeeNotificationDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeNotificationRepository
    extends JpaRepository<EmployeeNotificationDetail, Long> {
  
  List<EmployeeNotificationDetail> findByEmployeeId(String employeeId);
}
