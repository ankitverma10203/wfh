package com.radz.wfh.service.impl;

import com.radz.wfh.constant.EmployeeStatus;
import com.radz.wfh.dto.EmployeeRegistrationRequest;
import com.radz.wfh.model.EmployeeCredential;
import com.radz.wfh.model.EmployeeDetail;
import com.radz.wfh.repository.EmployeeCredentialRepository;
import com.radz.wfh.repository.EmployeeDetailRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.EmployeeRegistrationService;
import com.radz.wfh.utility.WfhUtility;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

  private final EmployeeDetailService employeeDetailService;
  private final EmployeeDetailRepository employeeDetailRepository;
  private final EmployeeCredentialRepository employeeCredentialRepository;

  public EmployeeRegistrationServiceImpl(
      EmployeeDetailService employeeDetailService,
      EmployeeDetailRepository employeeDetailRepository,
      EmployeeCredentialRepository employeeCredentialRepository) {
    this.employeeDetailService = employeeDetailService;
    this.employeeDetailRepository = employeeDetailRepository;
    this.employeeCredentialRepository = employeeCredentialRepository;
  }

  @Transactional
  @Override
  public EmployeeStatus register(EmployeeRegistrationRequest employeeRegistrationRequest) {

    String hashedPassword =
        WfhUtility.generateHashedValue(employeeRegistrationRequest.getPassword());
    employeeRegistrationRequest.setPassword(hashedPassword);

    EmployeeStatus status =
        employeeDetailService.doesAdminExist()
            ? EmployeeStatus.PENDING_APPROVAL
            : EmployeeStatus.ACTIVE;

    Long employeeId = saveEmployeeDetails(employeeRegistrationRequest, status);
    saveEmployeeCredential(employeeId, hashedPassword);
    return status;
  }

  private void saveEmployeeCredential(Long employeeId, String hashedPassword) {
    EmployeeCredential employeeCredential =
        EmployeeCredential.builder().employeeId(employeeId).password(hashedPassword).build();
    employeeCredentialRepository.save(employeeCredential);
  }

  private Long saveEmployeeDetails(
      EmployeeRegistrationRequest employeeRegistrationRequest, EmployeeStatus status) {
    EmployeeDetail employeeDetail =
        EmployeeDetail.builder()
            .name(employeeRegistrationRequest.getName())
            .role(employeeRegistrationRequest.getRole())
            .email(employeeRegistrationRequest.getEmail())
            .status(status)
            .build();

    employeeDetail = employeeDetailRepository.save(employeeDetail);
    return employeeDetail.getEmployeeId();
  }
}
