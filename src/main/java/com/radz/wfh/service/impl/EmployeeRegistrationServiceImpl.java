package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailRequest;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.EmployeeRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

  private final EmployeeDetailService employeeDetailService;
  private final EmployeeDetailRepository employeeDetailRepository;

  public EmployeeRegistrationServiceImpl(
      EmployeeDetailService employeeDetailService,
      EmployeeDetailRepository employeeDetailRepository) {
    this.employeeDetailService = employeeDetailService;
    this.employeeDetailRepository = employeeDetailRepository;
  }

  @Override
  public EmployeeStatus register(EmployeeDetailRequest employeeDetailRequest) {

    EmployeeStatus status =
        employeeDetailService.doesAdminExist()
            ? EmployeeStatus.PENDING_APPROVAL
            : EmployeeStatus.ACTIVE;

    EmployeeDetail employeeDetail =
        EmployeeDetail.builder()
            .name(employeeDetailRequest.getName())
            .role(employeeDetailRequest.getRole())
            .status(status)
            .build();

    employeeDetailRepository.save(employeeDetail);

    return status;
  }
}
