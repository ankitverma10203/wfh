package com.radz.wfh.service.impl;

import com.radz.wfh.constant.LoginStatus;
import com.radz.wfh.constant.WfhConstants;
import com.radz.wfh.dto.EmployeeLoginRequest;
import com.radz.wfh.dto.LoginResponse;
import com.radz.wfh.model.EmployeeCredential;
import com.radz.wfh.repository.EmployeeCredentialRepository;
import com.radz.wfh.service.EmployeeDetailService;
import com.radz.wfh.service.EmployeeLoginService;
import com.radz.wfh.utility.WfhUtility;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeLoginServiceImpl implements EmployeeLoginService {

  private final EmployeeCredentialRepository employeeCredentialRepository;
  private final EmployeeDetailService employeeDetailService;

  public EmployeeLoginServiceImpl(
      EmployeeCredentialRepository employeeCredentialRepository,
      EmployeeDetailService employeeDetailService) {
    this.employeeCredentialRepository = employeeCredentialRepository;
    this.employeeDetailService = employeeDetailService;
  }

  @Override
  public LoginResponse login(EmployeeLoginRequest employeeLoginRequest) {

    Optional<Long> employeeId =
        employeeDetailService.validateRequestedId(employeeLoginRequest.getId());

    if (employeeId.isEmpty()) {
      return LoginResponse.builder()
          .id(WfhConstants.INVALID_EMPLOYEE_ID)
          .name("")
          .loginStatus(LoginStatus.USER_NOT_FOUND)
          .build();
    }

    EmployeeCredential employeeCredential =
        employeeCredentialRepository.getEmployeeDetailAndCredentials(employeeId.get());

    LoginStatus loginStatus =
        WfhUtility.validatePassword(
                employeeLoginRequest.getPassword(), employeeCredential.getPassword())
            ? LoginStatus.LOGIN_SUCCESS
            : LoginStatus.INCORRECT_PASSWORD;

    return LoginResponse.builder()
        .loginStatus(loginStatus)
        .name(employeeCredential.getEmployeeDetail().getName())
        .id(employeeCredential.getEmployeeId())
        .role(employeeCredential.getEmployeeDetail().getRole())
        .build();
  }
}
