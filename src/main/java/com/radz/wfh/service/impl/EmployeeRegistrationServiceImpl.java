package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeDetailDto;
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
  public EmployeeStatus register(EmployeeDetailDto employeeDetailDto) {

    EmployeeStatus status =
        employeeDetailService.doesAdminExist()
            ? EmployeeStatus.PENDING_APPROVAL
            : EmployeeStatus.ACTIVE;

    EmployeeDetail employeeDetail =
        EmployeeDetail.builder()
            .name(employeeDetailDto.getName())
            .role(employeeDetailDto.getRole())
            .status(status)
            .build();

    employeeDetailRepository.save(employeeDetail);

    return status;
  }
}
