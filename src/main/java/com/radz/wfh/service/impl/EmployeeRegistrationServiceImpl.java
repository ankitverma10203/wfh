package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.constant.Role;
import com.radz.wfh.dto.EmployeeInfo;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeRegistrationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

  private final EmployeeDetailRepository employeeDetailRepository;

  public EmployeeRegistrationServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
    this.employeeDetailRepository = employeeDetailRepository;
  }

  @Transactional
  @Override
  public EmployeeStatus register(EmployeeInfo employeeInfo) {

    boolean isEmployeeDetailTableEmpty = employeeDetailRepository.count() == 0;

    EmployeeStatus status =
        isEmployeeDetailTableEmpty ? EmployeeStatus.ACTIVE : EmployeeStatus.PENDING_APPROVAL;
    Role role = isEmployeeDetailTableEmpty ? Role.ADMIN : Role.EMPLOYEE;
    String managerId = isEmployeeDetailTableEmpty ? employeeInfo.getSub() : "0";

    EmployeeDetail employeeDetail =
        EmployeeDetail.builder()
            .employeeId(employeeInfo.getSub())
            .name(employeeInfo.getName())
            .email(employeeInfo.getEmail())
            .status(status)
            .role(role)
            .managerId(managerId)
            .build();

    employeeDetailRepository.save(employeeDetail);
    return status;
  }
}
